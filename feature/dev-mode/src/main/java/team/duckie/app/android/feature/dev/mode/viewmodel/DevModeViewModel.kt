package team.duckie.app.android.feature.dev.mode.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.feature.dev.mode.viewmodel.sideeffect.SkeletonSideEffect
import team.duckie.app.android.feature.dev.mode.viewmodel.state.SkeletonState
import javax.inject.Inject

@HiltViewModel
class DevModeViewModel @Inject constructor(
) : ContainerHost<SkeletonState, SkeletonSideEffect>, ViewModel() {
    override val container =
        container<SkeletonState, SkeletonSideEffect>(SkeletonState.InputPassword)

    fun inputPassword() {

    }
}
