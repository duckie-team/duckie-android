package land.sungbin.androidprojecttemplate.ui.main.setting.vm

import land.sungbin.androidprojecttemplate.domain.constants.AccountType
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
) : BaseViewModel<SettingState, SettingSideEffect>() {

    suspend fun fetchSetting() {
        fetchSettingUseCase()
            .onSuccess { response ->
                setState {
                    copy(
                        activityNotifications = response.activityNotification,
                        messageNotifications = response.messageNotification,
                    )
                }
            }
            .onFailure {
                setEffect {
                    SettingSideEffect.FetchSettingFailed
                }
            }
    }

    suspend fun fetchAccountInformation() {
        fetchAccountInformationUseCase()
            .onSuccess { response ->
                setState {
                    copy(
                        accountType = response.accountType,
                        email = response.email,
                    )
                }
            }
            .onFailure {
                setEffect {
                    SettingSideEffect.FetchAccountInformationFailed
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
            setEffect {
                SettingSideEffect.PostSettingFailed
            }
        }
    }


    fun navigatePage(step: SettingStep) = setState {
        copy(
            currentStep = step,
        )
    }

    suspend fun changeActivityNotifications(state: Boolean) {
        setState {
            copy(
                activityNotifications = state
            )
        }

        postSetting()
    }

    suspend fun changeMessageNotifications(state: Boolean) {
        setState {
            copy(
                messageNotifications = state,
            )
        }

        postSetting()
    }

    override fun createInitialState(): SettingState {
        return SettingState(
            currentStep = SettingStep.SETTING_MAIN_SCREEN,
            activityNotifications = false,
            messageNotifications = false,
            accountType = AccountType.KAKAO,
            email = ""
        )
    }
}
