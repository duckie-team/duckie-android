/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("ConstPropertyName", "PrivatePropertyName")

package team.duckie.app.android.feature.ui.onboard.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.apache.commons.io.FileUtils
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.auth.usecase.AttachAccessTokenToHeaderUseCase
import team.duckie.app.android.domain.auth.usecase.JoinUseCase
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.category.usecase.GetCategoriesUseCase
import team.duckie.app.android.domain.file.constant.FileType
import team.duckie.app.android.domain.file.usecase.FileUploadUseCase
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.domain.kakao.usecase.GetKakaoAccessTokenUseCase
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.tag.usecase.TagCreateUseCase
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.usecase.NicknameDuplicateCheckUseCase
import team.duckie.app.android.domain.user.usecase.SetMeUseCase
import team.duckie.app.android.domain.user.usecase.UserUpdateUseCase
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState
import team.duckie.app.android.util.android.permission.PermissionCompat
import team.duckie.app.android.util.android.savedstate.SaveableMutableStateFlow
import team.duckie.app.android.util.android.viewmodel.context
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.kotlin.seconds

private val NextStepNavigateThrottle = 1.seconds
private const val ProfileImageCompressQuality = 100

private const val ImagePermissionGrantStateSavedKey = "ImagePermissionGrantState"

internal class OnboardViewModel @AssistedInject constructor(
    application: Application,
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val nicknameDuplicateCheckUseCase: NicknameDuplicateCheckUseCase,
    private val loadGalleryImagesUseCase: LoadGalleryImagesUseCase,
    private val joinUseCase: JoinUseCase,
    private val attachAccessTokenToHeaderUseCase: AttachAccessTokenToHeaderUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val fileUploadUseCase: FileUploadUseCase,
    private val tagCreateUseCase: TagCreateUseCase,
    private val userUpdateUseCase: UserUpdateUseCase,
    private val setMeUseCase: SetMeUseCase,
    @Assisted private val getKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase,
) : ContainerHost<OnboardState, OnboardSideEffect>, AndroidViewModel(application) {
    /* ----- Assisted ----- */
    @AssistedFactory
    interface OnboardViewModelFactory {
        fun create(
            getKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase,
            savedStateHandle: SavedStateHandle,
        ): OnboardViewModel
    }

    companion object Factory {
        class FactoryProvider(
            private val factory: OnboardViewModelFactory,
            private val getKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle,
            ): T {
                @Suppress("UNCHECKED_CAST")
                return factory.create(
                    getKakaoAccessTokenUseCase = getKakaoAccessTokenUseCase,
                    savedStateHandle = handle,
                ) as T
            }
        }
    }

    /* ----- Variable ----- */

    override val container = container<OnboardState, OnboardSideEffect>(
        initialState = OnboardState(),
        savedStateHandle = savedStateHandle,
    )

    private val duckieUserProfileImageTemporaryFile =
        File.createTempFile("temporary-duckie-user-profile-image", ".png", context.cacheDir)

    private val nicknameFilter = Regex("[^가-힣a-zA-Z0-9_.]")
    private var lastestUpdateStepMillis = System.currentTimeMillis()

    val galleryImages: ImmutableList<String> get() = container.stateFlow.value.galleryImages.toImmutableList()

    val imagePermissionGrantState = SaveableMutableStateFlow<Boolean?>(
        savedStateHandle = savedStateHandle,
        key = ImagePermissionGrantStateSavedKey,
        initialValue = null,
    )
    val isImagePermissionGranted: Boolean? get() = imagePermissionGrantState.value

    val imagePermission = PermissionCompat.getImageStoragePermission()
    var isCameraPermissionGranted = false

    val me get() = requireNotNull(container.stateFlow.value.me) { "User is not initialized." }

    // TODO(riflockle7): user 엔티티 commit
    val profileImageUrl get() = requireNotNull(container.stateFlow.value.me?.profileImageUrl) { "User.profileImageUrl is not initialized." }

    /* ----- Onboard Logic ----- */

    fun navigateStep(step: OnboardStep, ignoreThrottle: Boolean = false) = intent {
        if (!ignoreThrottle &&
            System.currentTimeMillis() - lastestUpdateStepMillis < NextStepNavigateThrottle
        ) {
            return@intent
        }
        lastestUpdateStepMillis = System.currentTimeMillis()
        reduce {
            state.copy(step = step)
        }
    }

    fun loadGalleryImages() = intent {
        loadGalleryImagesUseCase()
            .onSuccess { images ->
                postSideEffect(OnboardSideEffect.UpdateGalleryImages(images))
            }
            .onFailure { expection ->
                postSideEffect(OnboardSideEffect.ReportError(expection))
            }
    }

    fun addGalleryImages(images: List<String>) = intent {
        reduce {
            state.copy(galleryImages = images)
        }
    }

    fun finishOnboard(newMe: User) = intent {
        setMeUseCase(newMe)
        postSideEffect(OnboardSideEffect.FinishOnboard("${newMe.id}"))
    }

    // validation

    suspend fun nicknameDuplicateCheck(nickname: String) = intent {
        nicknameDuplicateCheckUseCase(nickname)
            .onSuccess { result ->
                postSideEffect(OnboardSideEffect.NicknameDuplicateChecked(result))
            }
            .attachExceptionHandling()
    }

    fun checkNicknameRuleError(nickname: String): Boolean {
        return nicknameFilter.containsMatchIn(nickname)
    }

    // user

    fun updateUserNickname(nickname: String) = intent {
        reduce {
            state.copy(temporaryNickname = nickname)
        }
    }

    fun updateUserSelectCategories(categories: List<Category>) = intent {
        reduce {
            state.copy(selectedCategories = categories)
        }
    }

    // from Uri
    fun updateUserProfileImageFile(fileUri: Uri) = intent {
        val file = duckieUserProfileImageTemporaryFile.also { it.delete() }
        val stream = context.contentResolver.openInputStream(fileUri)
        FileUtils.copyInputStreamToFile(stream, file)
        reduce {
            state.copy(temporaryProfileImageFile = file)
        }
    }

    // from Bitmap
    fun updateUserProfileImageFile(imageBitmap: Bitmap) = intent {
        val file = duckieUserProfileImageTemporaryFile.also { it.delete() }
        imageBitmap.compress(
            Bitmap.CompressFormat.PNG,
            ProfileImageCompressQuality,
            file.outputStream(),
        )
        reduce {
            state.copy(temporaryProfileImageFile = file)
        }
    }

    suspend fun updateUserFavoriteTags(names: List<String>): List<Result<Tag>> {
        return suspendCancellableCoroutine { continuation ->
            viewModelScope.launch {
                val tags = names.fastMap { name ->
                    async { createTag(name) }
                }.awaitAll()
                continuation.resume(tags)
            }
        }
    }

    /* ----- Permission ----- */

    fun updateImagePermissionGrantState(isGranted: Boolean?) {
        imagePermissionGrantState.value = isGranted
    }

    /* ----- Api ----- */

    suspend fun getKakaoAccessTokenAndJoin() = intent {
        getKakaoAccessTokenUseCase()
            .onSuccess { token ->
                postSideEffect(OnboardSideEffect.DelegateJoin(token))
            }
            .attachExceptionHandling()
    }

    suspend fun join(kakaoAccessToken: String) = intent {
        joinUseCase(kakaoAccessToken)
            .onSuccess { response ->
                reduce {
                    state.copy(me = response.user)
                }
                postSideEffect(OnboardSideEffect.UpdateAccessToken(response.accessToken))
                postSideEffect(OnboardSideEffect.AttachAccessTokenToHeader(response.accessToken))
                postSideEffect(
                    OnboardSideEffect.Joined(
                        isNewUser = response.isNewUser,
                        me = response.user,
                    ),
                )
            }
            .attachExceptionHandling()
    }

    fun attachAccessTokenToHeader(accessToken: String) = intent {
        attachAccessTokenToHeaderUseCase(accessToken).attachExceptionHandling()
    }

    suspend fun getCategories(withPopularTags: Boolean) = intent {
        getCategoriesUseCase(withPopularTags)
            .onSuccess { categories ->
                reduce {
                    state.copy(categories = categories)
                }
            }
            .attachExceptionHandling()
    }

    suspend fun uploadProfileImage(file: File): String {
        return suspendCancellableCoroutine { continuation ->
            viewModelScope.launch {
                fileUploadUseCase(file, FileType.Profile)
                    .onSuccess { url ->
                        continuation.resume(url)
                    }
                    .onFailure { exception ->
                        continuation.resumeWithException(exception)
                    }
            }
        }
    }

    private suspend fun createTag(name: String): Result<Tag> {
        return suspendCancellableCoroutine { continuation ->
            viewModelScope.launch {
                tagCreateUseCase(name)
                    .onSuccess { tag ->
                        continuation.resume(Result.success(tag))
                    }
                    .onFailure { exception ->
                        continuation.resume(Result.failure(exception))
                    }
            }
        }
    }

    // TODO(riflockle7): PATCH /users/:id API commit
    suspend fun updateUser(
        id: Int,
        favoriteCategories: List<Category>?,
        favoriteTags: List<Tag>?,
        profileImageUrl: String?,
        nickname: String?,
    ) = intent {
        userUpdateUseCase(
            id = id,
            categories = favoriteCategories,
            tags = favoriteTags,
            profileImageUrl = profileImageUrl,
            nickname = nickname,
            // TODO(riflockle7): 온보딩 완료 시에는 무조건 NEW -> READY 로 바꿔줘야 함
            status = "READY",
            updateMeInstance = { user -> viewModelScope.launch { setMeUseCase(user) } },
        )
            .onSuccess { user ->
                reduce {
                    state.copy(
                        me = user,
                        finishOnboarding = true,
                    )
                }
            }
            .attachExceptionHandling()
    }

    private fun Result<*>.attachExceptionHandling(
        additinal: (exception: Throwable) -> Unit = {},
    ) = intent {
        onFailure { exception ->
            postSideEffect(OnboardSideEffect.ReportError(exception))
            additinal(exception)
        }
    }
}
