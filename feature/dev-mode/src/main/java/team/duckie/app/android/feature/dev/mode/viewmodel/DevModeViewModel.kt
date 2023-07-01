package team.duckie.app.android.feature.dev.mode.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.feature.dev.mode.BuildConfig
import team.duckie.app.android.feature.dev.mode.viewmodel.sideeffect.DevModeSideEffect
import team.duckie.app.android.feature.dev.mode.viewmodel.state.DevModeState
import team.duckie.app.android.feature.dev.mode.viewmodel.state.DuckieApi
import team.duckie.app.android.feature.dev.mode.viewmodel.state.toDuckieApi
import javax.inject.Inject

@HiltViewModel
class DevModeViewModel @Inject constructor(
) : ContainerHost<DevModeState, DevModeSideEffect>, ViewModel() {
    override val container =
        container<DevModeState, DevModeSideEffect>(DevModeState.InputPassword())

    /** [DevModeState.InputPassword] 에서 입력한 내용을 변경한다. */
    fun editInputted(inputted: String) {
        intent { reduce { DevModeState.InputPassword(inputted) } }
    }

    /** 개발자 모드로 진입한다. */
    fun gotoDevMode(duckieApi: DuckieApi? = null) {
        val password = (container.stateFlow.value as? DevModeState.InputPassword)?.inputted

        if (password == BuildConfig.DEV_MODE_PASSWORD) {
            intent {
                reduce {
                    DevModeState.Success(
                        duckieApi = duckieApi ?: BuildConfig.IS_STAGE.toDuckieApi()!!,
                    )
                }
            }
        } else {
            intent { reduce { DevModeState.InputPassword() } }
        }
    }

    /** 현재 선택된 API 설정을 [duckieApi] 로 변경한다. */
    fun changeApi(duckieApi: DuckieApi) {
        val state = container.stateFlow.value
        require(state is DevModeState.Success)

        intent { reduce { state.copy(duckieApi = duckieApi) } }
    }

    /**
     * 개발자 모드 화면을 닫는다.
     * 해당 Dialog 를 호출하는 생명주기에 따르므로 반드시 이 초기화 작업을 해주어야 한다.
     */
    fun closeDevMode() {
        intent { reduce { DevModeState.InputPassword() } }
    }
}
