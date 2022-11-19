package land.sungbin.androidprojecttemplate.ui.main.setting.vm

import land.sungbin.androidprojecttemplate.domain.model.SettingEntity
import land.sungbin.androidprojecttemplate.domain.usecase.fetch.FetchAccountInformationUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.fetch.FetchSettingUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.score.UpdateSettingUseCase
import land.sungbin.androidprojecttemplate.shared.android.base.BaseViewModel
import land.sungbin.androidprojecttemplate.ui.main.setting.mvi.SettingSideEffect
import land.sungbin.androidprojecttemplate.ui.main.setting.mvi.SettingState
import land.sungbin.androidprojecttemplate.ui.main.setting.utils.SettingStep
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
