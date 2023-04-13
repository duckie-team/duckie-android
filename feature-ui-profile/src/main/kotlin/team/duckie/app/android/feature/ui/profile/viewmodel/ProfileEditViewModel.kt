/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.domain.user.usecase.GetUserUseCase
import team.duckie.app.android.domain.user.usecase.NicknameDuplicateCheckUseCase
import team.duckie.app.android.domain.user.usecase.NicknameValidationUseCase
import team.duckie.app.android.domain.user.usecase.UserUpdateUseCase
import team.duckie.app.android.feature.ui.profile.viewmodel.sideeffect.ProfileEditSideEffect
import team.duckie.app.android.feature.ui.profile.viewmodel.state.GalleryState
import team.duckie.app.android.feature.ui.profile.viewmodel.state.NicknameState
import team.duckie.app.android.feature.ui.profile.viewmodel.state.ProfileEditState
import team.duckie.app.android.shared.ui.compose.constant.SharedIcon
import team.duckie.app.android.util.kotlin.ImmutableList
import team.duckie.app.android.util.kotlin.MutableDebounceFlow
import team.duckie.app.android.util.kotlin.debounceAction
import team.duckie.app.android.util.kotlin.fastAll
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.ui.const.Extras
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val loadGalleryImagesUseCase: LoadGalleryImagesUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val userUpdateUseCase: UserUpdateUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val nicknameDuplicateCheckUseCase: NicknameDuplicateCheckUseCase,
    private val nicknameValidationUseCase: NicknameValidationUseCase,
) : ViewModel(), ContainerHost<ProfileEditState, ProfileEditSideEffect> {
    override val container: Container<ProfileEditState, ProfileEditSideEffect> =
        container(ProfileEditState())

    private lateinit var originNickname: String

    private val nickNameChangeEvent = MutableDebounceFlow<String>().apply {
        debounceAction(
            coroutineScope = viewModelScope,
            timeoutMillis = 500L,
            action = { nickname ->
                nicknameValidationProcess(nickname)
            }
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
                    }
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
                    }
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
            getUser()
        }
    }

    fun clickEditProfile() = intent {
        loadGalleryImages()
    }

    private fun getUser() = intent {
        updateLoading(true)
        getUserUseCase(state.userId).onSuccess { user ->
            reduce {
                state.copy(
                    profile = user.profileImageUrl,
                    nickname = user.nickname,
                    introduce = user.introduction ?: "",
                )
            }
            originNickname = user.nickname
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
                )
            )
            changePhotoPickerVisible(true)
        }.onFailure {
            postSideEffect(ProfileEditSideEffect.ReportError(it))
        }
    }

    fun changeProfile(profile: Any?) = intent {
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
            )
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

    fun clickEditComplete() = intent {
        state.run {
            val profileUrl = if (profile is SharedIcon) {
                ""
            } else {
                profile.toString()
            }
            if (state.nicknameState.isInValid()) return@intent
            userUpdateUseCase(
                id = userId,
                profileImageUrl = profileUrl,
                categories = null,
                tags = null,
                status = null,
                nickname = nickname,
                introduction = introduce,
            ).onSuccess {
                postSideEffect(ProfileEditSideEffect.NavigateBack)
            }.onFailure {
                postSideEffect(ProfileEditSideEffect.ReportError(it))
            }
        }
    }

    private fun updateLoading(isLoading: Boolean) = intent {
        reduce {
            state.copy(isLoading = isLoading)
        }
    }
}
