import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.feature.skeleton.viewmodel.sideeffect.SkeletonSideEffect
import team.duckie.app.android.feature.skeleton.viewmodel.state.SkeletonState
import javax.inject.Inject

@HiltViewModel
internal class SkeletonViewModel @Inject constructor() :
    ContainerHost<SkeletonState, SkeletonSideEffect>, ViewModel() {
    override val container = container<SkeletonState, SkeletonSideEffect>(SkeletonState.Loading)
}
