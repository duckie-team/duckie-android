package team.duckie.app.android.ui.main.setting.vm

import team.duckie.app.domain.model.SettingEntity
import team.duckie.app.domain.usecase.fetch.FetchAccountInformationUseCase
import team.duckie.app.domain.usecase.fetch.FetchSettingUseCase
import team.duckie.app.domain.usecase.score.UpdateSettingUseCase
import team.duckie.app.shared.android.base.BaseViewModel
import team.duckie.app.ui.main.setting.mvi.SettingSideEffect
import team.duckie.app.ui.main.setting.mvi.SettingState
import team.duckie.app.ui.main.setting.utils.SettingStep
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingViewModel @Inject constructor(
    private val fetchSettingUseCase: FetchSettingUseCase,
    private val updateSettingUseCase: UpdateSettingUseCase,
    private val fetchAccountInformationUseCase: FetchAccountInformationUseCase,
) : BaseViewModel<SettingState, SettingSideEffect>(SettingState()) {

    suspend fun fetchSetting() {
        fetchSettingUseCase()
            .onSuccess { response ->
                updateState {
                    copy(
                        activityNotifications = response.activityNotification,
                        messageNotifications = response.messageNotification,
                    )
                }
            }
            .onFailure {
                postSideEffect {
                    SettingSideEffect.FetchSetting
                }
            }
    }

    suspend fun fetchAccountInformation() {
        fetchAccountInformationUseCase()
            .onSuccess { response ->
                updateState {
                    copy(
                        accountType = response.accountType,
                        email = response.email,
                    )
                }
            }
            .onFailure {
                postSideEffect {
                    SettingSideEffect.FetchAccountInformation
                }
            }
    }


    private suspend fun postSetting() {
        updateSettingUseCase(
            entity = SettingEntity(
                activityNotification = state.value.activityNotifications,
                messageNotification = state.value.messageNotifications,
            )
        ).onFailure {
            postSideEffect {
                SettingSideEffect.PostSetting
            }
        }
    }


    fun navigatePage(step: SettingStep) = updateState {
        copy(
            currentStep = step,
        )
    }

    suspend fun changeActivityNotifications(state: Boolean) {
        updateState {
            copy(
                activityNotifications = state
            )
        }

        postSetting()
    }

    suspend fun changeMessageNotifications(state: Boolean) {
        updateState {
            copy(
                messageNotifications = state,
            )
        }

        postSetting()
    }
}
