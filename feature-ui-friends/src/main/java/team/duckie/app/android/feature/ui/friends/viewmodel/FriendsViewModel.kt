/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.friends.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.feature.ui.friends.viewmodel.sideeffect.FriendsSideEffect
import team.duckie.app.android.feature.ui.friends.viewmodel.state.FriendsState
import javax.inject.Inject

@HiltViewModel
internal class FriendsViewModel @Inject constructor(
    // private val followUseCase: FollowUseCase,
) : ContainerHost<FriendsState, FriendsSideEffect>, ViewModel() {
    override val container = container<FriendsState, FriendsSideEffect>(FriendsState())

    fun setSelectedTab(index: Int) = intent {
        reduce {
            state.copy(selectedTab = index)
        }
    }

    fun clickAppBarRightIcon() = intent {}
    /*private fun updateLoading(loading: Boolean) = intent {
        reduce {
            state.copy(
                isLoading = loading,
            )
        }
    }*/
}
