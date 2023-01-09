/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.properties.Delegates
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.apache.commons.io.FileUtils
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.di.repository.ProvidesModule
import team.duckie.app.android.di.usecase.kakao.KakaoUseCaseModule
import team.duckie.app.android.domain.auth.usecase.AttachAccessTokenToHeaderUseCase
import team.duckie.app.android.domain.auth.usecase.JoinUseCase
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.category.usecase.GetCategoriesUseCase
import team.duckie.app.android.domain.file.usecase.FileUploadUseCase
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.tag.usecase.TagCreateUseCase
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.usecase.UserUpdateUseCase
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.impl.ApiViewModelInstance
import team.duckie.app.android.feature.ui.onboard.viewmodel.impl.PermissionViewModelInstance
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState
import team.duckie.app.android.util.kotlin.cancelChildrenAndItself
import team.duckie.app.android.util.kotlin.fastForEach
import team.duckie.app.android.util.kotlin.seconds

private val NextStepNavigateThrottle = 1.seconds
private const val ProfileImageCompressQuality = 100

// TODO(sungbin): AndroidViewModel + viewModelScope
// FIXME(sungbin): ViewModel constructor should be annotated with @Inject instead of @AssistedInject.
@Suppress("StaticFieldLeak")
@HiltViewModel
internal class OnboardViewModel @Inject constructor(
    private val loadGalleryImagesUseCase: LoadGalleryImagesUseCase,
    private val joinUseCase: JoinUseCase,
    private val attachAccessTokenToHeaderUseCase: AttachAccessTokenToHeaderUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val fileUploadUseCase: FileUploadUseCase,
    private val tagCreateUseCase: TagCreateUseCase,
    private val userUpdateUseCase: UserUpdateUseCase,
    @ApplicationContext private val context: Context,
    // @Assisted private val getKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase,
) : ContainerHost<OnboardState, OnboardSideEffect>,
    ViewModel(),
    PermissionViewModel by PermissionViewModelInstance,
    ApiViewModel by ApiViewModelInstance(
        // TODO(sungbin): FIXME
        getKakaoAccessTokenUseCase = KakaoUseCaseModule.provideGetKakaoAccessTokenUseCase(
            ProvidesModule.provideKakaoRepository(
                context as Activity
            )
        ),
        joinUseCase = joinUseCase,
        attachAccessTokenToHeaderUseCase = attachAccessTokenToHeaderUseCase,
        getCategoriesUseCase = getCategoriesUseCase,
        fileUploadUseCase = fileUploadUseCase,
        tagCreateUseCase = tagCreateUseCase,
        userUpdateUseCase = userUpdateUseCase,
    ) {

    override val container = container<OnboardState, OnboardSideEffect>(OnboardState.Initial)

    // TODO(sungbin): implement
    // init {
    //     setEventHandler { event ->
    //         updateState { event }
    //     }

    //     setSideEffectHandler { effect ->
    //         postSideEffect { effect }
    //     }

    //     setExceptionHandler { exception ->
    //         updateState {
    //             OnboardState.Error(exception)
    //         }
    //         postSideEffect {
    //             OnboardSideEffect.ReportError(exception)
    //         }
    //     }
    // }

    private val duckieUserProfileImageTemporaryFile =
        File.createTempFile("temporary-duckie-user-profile-image", ".png", context.cacheDir)

    private val nicknameFilter = Regex("[^가-힣a-zA-Z0-9_.]")
    private var lastestUpdateStepMillis = System.currentTimeMillis()

    private var _galleryImages = persistentListOf<String>()
    val galleryImages: ImmutableList<String> get() = _galleryImages

    var me by Delegates.notNull<User>()
    var categories by Delegates.notNull<ImmutableList<Category>>()
    var selectedCategories: ImmutableList<Category> = persistentListOf()

    fun navigateStep(step: OnboardStep, ignoreThrottle: Boolean = false) = intent {
        if (!ignoreThrottle &&
            System.currentTimeMillis() - lastestUpdateStepMillis < NextStepNavigateThrottle
        ) return@intent
        lastestUpdateStepMillis = System.currentTimeMillis()
        reduce {
            OnboardState.NavigateStep(step)
        }
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

    suspend fun updateUserProfileImage(coroutineScope: CoroutineScope) {
        suspendCancellableCoroutine { continuation ->
            val file = me.temporaryProfileImageFile ?: return@suspendCancellableCoroutine continuation.resume(Unit)
            val job = coroutineScope.launch {
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

    suspend fun updateUserFavorateTags(
        favorateTagNames: List<String>,
        coroutineScope: CoroutineScope,
    ) {
        suspendCancellableCoroutine { continuation ->
            val favorateTagSize = favorateTagNames.size
            val favorateTags = ArrayList<Tag>(favorateTagSize)
            val job = coroutineScope.launch {
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
}
