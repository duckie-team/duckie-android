package land.sungbin.androidprojecttemplate.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import land.sungbin.androidprojecttemplate.common.UiStatus
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.home.component.dummyFeeds
import land.sungbin.androidprojecttemplate.home.component.dummyTags
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.quackquack.ui.component.QuackBottomSheetItem

class HomeViewModel : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container: Container<HomeState, HomeSideEffect> = container(HomeState())

    init {
        intent {
            delay(2000L)
            reduce {
                state.copy(
                    status = UiStatus.Success,
                    feeds = dummyFeeds,
                    interestedTags = dummyTags,
                )
            }
        }
    }

    fun changeSelectedUser(user: String) = intent {
        reduce {
            state.copy(selectedUser = user)
        }
    }

    fun deleteTag(index: Int) = intent {
        reduce {
            state.copy(
                interestedTags = state.interestedTags.apply {
                    this - this[index]
                }
            )
        }
    }

    fun selectFilterBottomSheet(bottomSheetItem: QuackBottomSheetItem) = intent {
        reduce {
            state.copy(
                filterBottomSheetItems = state.filterBottomSheetItems.map { item: QuackBottomSheetItem ->
                    if (bottomSheetItem == item) {
                        QuackBottomSheetItem(item.title, true)
                    } else {
                        QuackBottomSheetItem(item.title, false)
                    }
                }
            )
        }
    }

    fun selectMoreBottomSheet(bottomSheetItem: QuackBottomSheetItem) = intent {
        reduce {
            state.copy(
                moreBottomSheetItems = state.moreBottomSheetItems.map { item: QuackBottomSheetItem ->
                    if (bottomSheetItem == item) {
                        QuackBottomSheetItem(item.title, item.isImportant)
                    } else {
                        QuackBottomSheetItem(item.title, item.isImportant)
                    }
                }
            )
        }
    }
}