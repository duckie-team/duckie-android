/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.filterNot
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.android.image.MediaUtil
import team.duckie.app.android.common.android.ui.const.Debounce
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.compose.ui.constant.SharedIcon
import team.duckie.app.android.common.kotlin.ImmutableList
import team.duckie.app.android.common.kotlin.MutableDebounceFlow
import team.duckie.app.android.common.kotlin.debounceAction
import team.duckie.app.android.common.kotlin.fastAll
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.domain.file.constant.FileType
import team.duckie.app.android.domain.file.usecase.FileUploadUseCase
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.domain.user.usecase.GetUserUseCase
import team.duckie.app.android.domain.user.usecase.NicknameDuplicateCheckUseCase
import team.duckie.app.android.domain.user.usecase.NicknameValidationUseCase
import team.duckie.app.android.domain.user.usecase.UserUpdateUseCase
import team.duckie.app.android.feature.profile.viewmodel.sideeffect.ProfileEditSideEffect
import team.duckie.app.android.feature.profile.viewmodel.state.GalleryState
import team.duckie.app.android.feature.profile.viewmodel.state.NicknameState
import team.duckie.app.android.feature.profile.viewmodel.state.ProfileEditState
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val loadGalleryImagesUseCase: LoadGalleryImagesUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val userUpdateUseCase: UserUpdateUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val nicknameDuplicateCheckUseCase: NicknameDuplicateCheckUseCase,
    private val nicknameValidationUseCase: NicknameValidationUseCase,
    private val fileUploadUseCase: FileUploadUseCase,
) : ViewModel(), ContainerHost<ProfileEditState, ProfileEditSideEffect> {
    override val container: Container<ProfileEditState, ProfileEditSideEffect> =
        container(ProfileEditState())

    private lateinit var originNickname: String
    private var originProfileImageUrl: Any = SharedIcon.ic_default_profile

    private val nickNameChangeEvent = MutableDebounceFlow<String>().apply {
        debounceAction(
            coroutineScope = viewModelScope,
            timeoutMillis = Debounce.SearchSecond,
            builder = { filterNot(String::isEmpty) },
            action = { nickname ->
                nicknameValidationProcess(nickname)
            },
        )
    }

    private fun nicknameValidationProcess(nickName: String) = intent {
        nicknameValidationUseCase(nickName).let { isError ->
            reduce {
                state.copy(
                    nicknameState = if (isError) {
                        NicknameState.NicknameRuleError
                    } else {
                        NicknameState.Valid
                    },
                )
            }
        }
        nicknameDuplicateCheckUseCase(nickName).onSuccess {
            reduce {
                state.copy(
                    nicknameState = if (it.not() && nickName != originNickname) {
                        NicknameState.NicknameDuplicateError
                    } else if (state.nicknameState == NicknameState.NicknameRuleError) {
                        NicknameState.NicknameRuleError
                    } else {
                        NicknameState.Valid
                    },
                )
            }
        }.onFailure {
            postSideEffect(ProfileEditSideEffect.ReportError(it))
        }
    }

    init {
        intent {
            updateLoading(true)
            val userId = savedStateHandle.getStateFlow(Extras.UserId, 0).value
            reduce {
                state.copy(userId = userId)
            }
            initUser()
        }
    }

    fun clickEditProfile() = intent {
        loadGalleryImages()
    }

    private fun initUser() = intent {
        updateLoading(true)
        getUserUseCase(state.userId).onSuccess { user ->
            originNickname = user.nickname
            originProfileImageUrl = user.profileImageUrl ?: SharedIcon.ic_default_profile

            reduce {
                state.copy(
                    profile = originProfileImageUrl,
                    nickname = user.nickname,
                    introduce = user.introduction ?: "",
                )
            }
        }.onFailure {
            postSideEffect(ProfileEditSideEffect.ReportError(it))
        }.also {
            updateLoading(false)
        }
    }

    private fun loadGalleryImages() = intent {
        loadGalleryImagesUseCase().onSuccess { images ->
            changeGalleryState(
                galleryState = state.galleryState.copy(
                    images = persistentListOf(*images.toTypedArray()),
                    imagesSelections = images.fastMap { false }.toImmutableList(),
                ),
            )
            changePhotoPickerVisible(true)
        }.onFailure {
            postSideEffect(ProfileEditSideEffect.ReportError(it))
        }
    }

    fun changeProfile(profile: Any) = intent {
        reduce {
            state.copy(profile = profile)
        }
    }

    fun changeNickName(nickname: String) = intent {
        reduce {
            state.copy(nickname = nickname)
        }
        nickNameChangeEvent.emit(nickname)
    }

    fun changePhotoPickerVisible(visible: Boolean) = intent {
        changeGalleryState(galleryState = state.galleryState.copy(visible = visible))
    }

    fun clickGalleryImage(index: Int) = intent {
        changeGalleryState(
            galleryState = state.galleryState.copy(
                imagesSelections = ImmutableList(state.galleryState.imagesSelections.size) { i ->
                    i == index
                },
                selectedIndex = index,
            ),
        )
    }

    fun addProfileFromGallery() = intent {
        if (state.galleryState.imagesSelections.fastAll { !it }) return@intent
        changeProfile(state.galleryState.images[state.galleryState.selectedIndex])
        changePhotoPickerVisible(false)
    }

    fun inputIntroduce(introduce: String) = intent {
        reduce {
            state.copy(introduce = introduce)
        }
    }

    private fun changeGalleryState(galleryState: GalleryState) = intent {
        reduce {
            state.copy(galleryState = galleryState)
        }
    }

    fun clickBackPress() = intent {
        postSideEffect(ProfileEditSideEffect.NavigateBack)
    }

    fun clickEditComplete(applicationContext: Context?) = intent {
        updateLoading(true)

        getUploadableFileUrl(
            state.profile.toString(),
            applicationContext,
        ).onSuccess { uploadableUrl ->
            if (state.nicknameState.isInValid()) return@intent

            userUpdateUseCase(
                id = state.userId,
                profileImageUrl = uploadableUrl,
                categories = null,
                tags = null,
                status = null,
                nickname = state.nickname,
                introduction = state.introduce,
            ).onSuccess {
                updateLoading(false)
                postSideEffect(ProfileEditSideEffect.NavigateBack)
            }.onFailure {
                updateLoading(false)
                postSideEffect(ProfileEditSideEffect.ReportError(it))
            }
        }.onFailure {
            updateLoading(false)
            postSideEffect(ProfileEditSideEffect.ReportError(it))
        }
    }

    private fun updateLoading(isLoading: Boolean) = intent {
        reduce {
            state.copy(isLoading = isLoading)
        }
    }

    /**
     * 업로드 가능한 파일 URL 을 가져온다.
     *
     * @param profile 프로필 URL
     * @param applicationContext 프로필 업로드 로직을 위한 ApplicationContext
     */
    // TODO(riflockle7): 추후 공통화하기
    private suspend fun getUploadableFileUrl(
        profile: Any?,
        applicationContext: Context?,
    ): Result<String> {
        // SharedIcon 이거나 profile 자체가 null 인 경우
        if (profile is SharedIcon || profile == null) {
            return Result.success("")
        }
        require(profile is String)

        val startsWithHttp = profile.startsWith("http://") || profile.startsWith("https://")
        val profileNotChanged = originProfileImageUrl == profile

        // http 로 시작하는 데 기존값과 차이가 없는 경우 별도 업로드 진행하지 않음
        return if (startsWithHttp && profileNotChanged) {
            Result.success(profile)
        }
        // 그 외에는 무조건 서버 업로드 후 진행
        else {
            requireNotNull(applicationContext)
            val tempFile = MediaUtil.getOptimizedBitmapFile(applicationContext, Uri.parse(profile))
            return fileUploadUseCase(tempFile, FileType.Profile)
        }
    }
}
