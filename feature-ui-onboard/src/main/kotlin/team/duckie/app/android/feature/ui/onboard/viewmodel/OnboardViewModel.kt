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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
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
import team.duckie.app.android.domain.user.usecase.NicknameDuplicateCheckUseCase
import team.duckie.app.android.domain.user.usecase.UserUpdateUseCase
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState
import team.duckie.app.android.util.android.permission.PermissionCompat
import team.duckie.app.android.util.android.savedstate.SaveableMutableStateFlow
import team.duckie.app.android.util.android.viewmodel.context
import team.duckie.app.android.util.kotlin.cancelChildrenAndItself
import team.duckie.app.android.util.kotlin.fastForEach
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

    private var imagePermissionGrantState by SaveableMutableStateFlow<Boolean?>(
        savedStateHandle = savedStateHandle,
        key = ImagePermissionGrantStateSavedKey,
        initialValue = null,
    )
    val isImagePermissionGranted: Boolean? get() = imagePermissionGrantState

    val imagePermission = PermissionCompat.getImageStoragePermission()
    var isCameraPermissionGranted = false

    val me get() = requireNotNull(container.stateFlow.value.me) { "User is not initialized." }

    /* ----- Onboard Logic ----- */

    fun navigateStep(step: OnboardStep, ignoreThrottle: Boolean = false) = intent {
        if (!ignoreThrottle &&
            System.currentTimeMillis() - lastestUpdateStepMillis < NextStepNavigateThrottle
        ) {
            return@intent
        }
        lastestUpdateStepMillis = System.currentTimeMillis()
        postSideEffect(OnboardSideEffect.NavigateStep(step))
    }

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
        imageBitmap.compress(Bitmap.CompressFormat.PNG, ProfileImageCompressQuality, file.outputStream())
        reduce {
            state.copy(temporaryProfileImageFile = file)
        }
    }

    suspend fun updateUserProfileImage() = intent {
        suspendCancellableCoroutine { continuation ->
            // 유저가 프사 등록을 건너뛴 경우
            val file = state.temporaryProfileImageFile ?: return@suspendCancellableCoroutine continuation.resume(Unit)
            val job = viewModelScope.launch {
                launch {
                    container.sideEffectFlow.collect { sideEffect ->
                        if (sideEffect is OnboardSideEffect.PrfileImageUploaded) {
                            reduce {
                                state.copy(temporaryProfileImageUrl = sideEffect.url)
                            }
                            continuation.resume(Unit)
                        }
                    }
                }

                launch {
                    updateProfileImageFile(file)
                    state.temporaryProfileImageFile?.delete()
                    reduce {
                        state.copy(temporaryProfileImageFile = null)
                    }
                }
            }

            continuation.invokeOnCancellation {
                job.cancelChildrenAndItself()
            }
        }
    }

    suspend fun updateUserFavorateTags(favorateTagNames: List<String>) = intent {
        suspendCancellableCoroutine { continuation ->
            val favorateTagSize = favorateTagNames.size
            val favorateTags = ArrayList<Tag>(favorateTagSize)
            val job = viewModelScope.launch {
                launch {
                    container.sideEffectFlow.collect { sideEffect ->
                        if (sideEffect is OnboardSideEffect.TagCreated) {
                            favorateTags.add(sideEffect.tag)

                            if (favorateTags.size == favorateTagSize) {
                                reduce {
                                    state.copy(temporaryFavoriteTags = favorateTags)
                                }
                                continuation.resume(Unit)
                            }
                        }
                    }
                }

                favorateTagNames.fastForEach { name ->
                    launch {
                        createTag(name)
                    }
                }
            }

            continuation.invokeOnCancellation {
                job.cancelChildrenAndItself()
            }
        }
    }

    fun finishOnboard() = intent {
        postSideEffect(OnboardSideEffect.FinishOnboard)
    }

    /* ----- Permission ----- */

    fun updateImagePermissionGrantState(isGranted: Boolean?) {
        imagePermissionGrantState = isGranted
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
                postSideEffect(OnboardSideEffect.UpdateUser(response.user))
                postSideEffect(OnboardSideEffect.UpdateAccessToken(response.accessToken))
                postSideEffect(OnboardSideEffect.AttachAccessTokenToHeader(response.accessToken))
                postSideEffect(OnboardSideEffect.Joined(response.isNewUser))
            }
            .attachExceptionHandling()
    }

    fun attachAccessTokenToHeader(accessToken: String) = intent {
        attachAccessTokenToHeaderUseCase(accessToken).attachExceptionHandling()
    }

    suspend fun getCategories(withPopularTags: Boolean) = intent {
        getCategoriesUseCase(withPopularTags)
            .onSuccess { categories ->
                postSideEffect(OnboardSideEffect.CategoriesLoaded(categories))
            }
            .attachExceptionHandling()
    }

    private suspend fun updateProfileImageFile(file: File) = intent {
        fileUploadUseCase(file, FileType.Profile)
            .onSuccess { url ->
                postSideEffect(OnboardSideEffect.PrfileImageUploaded(url))
            }
            .attachExceptionHandling()
    }

    private suspend fun createTag(name: String) = intent {
        tagCreateUseCase(name)
            .onSuccess { tag ->
                postSideEffect(OnboardSideEffect.TagCreated(tag))
            }
            .attachExceptionHandling()
    }

    suspend fun updateUser(
        id: Int,
        nickname: String?,
        profileImageUrl: String?,
        favoriteCategories: List<Category>?,
        favoriteTags: List<Tag>?,
    ) = intent {
        userUpdateUseCase(
            id = id,
            nickname = nickname,
            profileImageUrl = profileImageUrl,
            favoriteCategories = favoriteCategories,
            favoriteTags = favoriteTags,
        )
            .onSuccess { user ->
                postSideEffect(OnboardSideEffect.UpdateUser(user))
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
