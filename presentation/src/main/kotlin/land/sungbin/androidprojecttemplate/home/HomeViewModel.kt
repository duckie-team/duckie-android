package land.sungbin.androidprojecttemplate.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import land.sungbin.androidprojecttemplate.common.UiStatus
import land.sungbin.androidprojecttemplate.home.component.dummyFeeds
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class HomeViewModel : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container: Container<HomeState, HomeSideEffect> = container(HomeState())

    init {
        intent {
            delay(2000L)
            reduce {
                state.copy(
                    status = UiStatus.Success,
                    feeds = dummyFeeds
                )
            }
        }
    }
}