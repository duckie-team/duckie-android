package land.sungbin.androidprojecttemplate.home

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class HomeViewModel : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container: Container<HomeState, HomeSideEffect> = container(HomeState())
}