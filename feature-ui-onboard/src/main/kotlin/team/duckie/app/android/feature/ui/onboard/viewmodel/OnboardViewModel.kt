/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

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
import kotlin.properties.Delegates
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
import team.duckie.app.android.domain.user.usecase.UserUpdateUseCase
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState
import team.duckie.app.android.util.android.permission.PermissionCompat
import team.duckie.app.android.util.android.viewmodel.context
import team.duckie.app.android.util.kotlin.cancelChildrenAndItself
import team.duckie.app.android.util.kotlin.fastForEach
import team.duckie.app.android.util.kotlin.seconds

private val NextStepNavigateThrottle = 1.seconds
private const val ProfileImageCompressQuality = 100

internal class OnboardViewModel @AssistedInject constructor(
    application: Application,
    @Suppress("UNUSED_PARAMETER") @Assisted savedStateHandle: SavedStateHandle,
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

    override val container = container<OnboardState, OnboardSideEffect>(OnboardState.Initial)

    private val duckieUserProfileImageTemporaryFile =
        File.createTempFile("temporary-duckie-user-profile-image", ".png", application.cacheDir)

    private val nicknameFilter = Regex("[^가-힣a-zA-Z0-9_.]")
    private var lastestUpdateStepMillis = System.currentTimeMillis()

    private var _galleryImages = persistentListOf<String>()
    val galleryImages: ImmutableList<String> get() = _galleryImages

    private val mutableImagePermissionGrantState = MutableStateFlow<Boolean?>(null)
    val imagePermissionGrantState = mutableImagePermissionGrantState.asStateFlow()
    val isImagePermissionGranted get() = imagePermissionGrantState.value

    val imagePermission = PermissionCompat.getImageStoragePermission()
    var isCameraPermissionGranted = false

    var me by Delegates.notNull<User>()
    var categories by Delegates.notNull<ImmutableList<Category>>()
    var selectedCategories: ImmutableList<Category> = persistentListOf()

    /* ----- Onboard Logic ----- */

    fun navigateStep(step: OnboardStep, ignoreThrottle: Boolean = false) = intent {
        if (!ignoreThrottle &&
            System.currentTimeMillis() - lastestUpdateStepMillis < NextStepNavigateThrottle
        ) {
            return@intent
        }
        lastestUpdateStepMillis = System.currentTimeMillis()
        reduce {
            OnboardState.NavigateStep(step)
        }
    }

    suspend fun nicknameDuplicateCheck(nickname: String) = intent {
        nicknameDuplicateCheckUseCase(nickname)
            .onSuccess { result ->
                reduce { OnboardState.NicknameDuplicateChecked(result) }
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
                reduce {
                    OnboardState.Error(expection)
                }
                postSideEffect(OnboardSideEffect.ReportError(expection))
            }
    }

    fun addGalleryImages(images: List<String>) {
        _galleryImages = _galleryImages.addAll(images)
    }

    fun updateUserProfileImageFile(fileUri: Uri) {
        val file = duckieUserProfileImageTemporaryFile.also { it.delete() }
        val stream = context.contentResolver.openInputStream(fileUri)
        FileUtils.copyInputStreamToFile(stream, file)
        me.temporaryProfileImageFile = file
    }

    fun updateUserProfileImageFile(imageBitmap: Bitmap) {
        val file = duckieUserProfileImageTemporaryFile.also { it.delete() }
        imageBitmap.compress(Bitmap.CompressFormat.PNG, ProfileImageCompressQuality, file.outputStream())
        me.temporaryProfileImageFile = file
    }

    suspend fun updateUserProfileImage() {
        suspendCancellableCoroutine { continuation ->
            val file = me.temporaryProfileImageFile ?: return@suspendCancellableCoroutine continuation.resume(Unit)
            val job = viewModelScope.launch {
                launch {
                    container.stateFlow.collect { state ->
                        if (state is OnboardState.PrfileImageUploaded) {
                            me.temporaryProfileImageUrl = state.url
                            continuation.resume(Unit)
                        }
                    }
                }

                launch {
                    updateProfileImageFile(file)
                    me.temporaryProfileImageFile?.delete()
                }
            }

            continuation.invokeOnCancellation {
                job.cancelChildrenAndItself()
            }
        }
    }

    suspend fun updateUserFavorateTags(favorateTagNames: List<String>) {
        suspendCancellableCoroutine { continuation ->
            val favorateTagSize = favorateTagNames.size
            val favorateTags = ArrayList<Tag>(favorateTagSize)
            val job = viewModelScope.launch {
                launch {
                    container.stateFlow.collect { state ->
                        if (state is OnboardState.TagCreated) {
                            favorateTags.add(state.tag)

                            if (favorateTags.size == favorateTagSize) {
                                me.temporaryFavoriteTags = favorateTags
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
        mutableImagePermissionGrantState.value = isGranted
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
                reduce { OnboardState.Joined(response.isNewUser) }
                postSideEffect(OnboardSideEffect.UpdateUser(response.user))
                postSideEffect(OnboardSideEffect.UpdateAccessToken(response.accessToken))
                postSideEffect(OnboardSideEffect.AttachAccessTokenToHeader(response.accessToken))
            }
            .attachExceptionHandling()
    }

    fun attachAccessTokenToHeader(accessToken: String) = intent {
        attachAccessTokenToHeaderUseCase(accessToken).attachExceptionHandling()
    }

    suspend fun getCategories(withPopularTags: Boolean) = intent {
        getCategoriesUseCase(withPopularTags)
            .onSuccess { categories ->
                reduce { OnboardState.CategoriesLoaded(categories) }
            }
            .attachExceptionHandling()
    }

    private suspend fun updateProfileImageFile(file: File) = intent {
        fileUploadUseCase(file, FileType.Profile)
            .onSuccess { url ->
                reduce { OnboardState.PrfileImageUploaded(url) }
            }
            .attachExceptionHandling()
    }

    private suspend fun createTag(name: String) = intent {
        tagCreateUseCase(name)
            .onSuccess { tag ->
                reduce { OnboardState.TagCreated(tag) }
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
            reduce { OnboardState.Error(exception) }
            postSideEffect(OnboardSideEffect.ReportError(exception))
            additinal(exception)
        }
    }
}
