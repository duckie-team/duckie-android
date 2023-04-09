/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.friends.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.usecase.FetchUserFollowersUseCase
import team.duckie.app.android.domain.user.usecase.FetchUserFollowingsUseCase
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.ui.friends.viewmodel.sideeffect.FriendsSideEffect
import team.duckie.app.android.feature.ui.friends.viewmodel.state.FriendsState
import team.duckie.app.android.util.kotlin.FriendsType
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.ui.const.Extras
import javax.inject.Inject

@HiltViewModel
internal class FriendsViewModel @Inject constructor(
    // private val followUseCase: FollowUseCase,
    private val fetchUserFollowersUseCase: FetchUserFollowersUseCase,
    private val fetchUserFollowingsUseCase: FetchUserFollowingsUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<FriendsState, FriendsSideEffect>, ViewModel() {
    override val container = container<FriendsState, FriendsSideEffect>(FriendsState())

    init {
        val userId = savedStateHandle.getStateFlow(Extras.userId, 0)
        initState(userId.value)
    }

    private fun initState(
        userId: Int,
    ) = intent {
        val result = getMeUseCase()
            .onSuccess { me ->
                reduce { state.copy(me = me) }
            }
            .onFailure {
                postSideEffect(FriendsSideEffect.ReportError(it))
            }
        if (result.isSuccess) {
            val myId = result.getOrNull()?.id ?: 0
            if (userId == myId) {
                fetchUserFollowers(myId)
                fetchUserFollowings(myId)
            } else {
                fetchUserFollowers(userId)
                fetchUserFollowings(userId)
            }
        }
    }

    fun setSelectedTab(type: FriendsType) = intent {
        reduce {
            state.copy(selectedTab = type)
        }
    }

    private fun fetchUserFollowers(userId: Int) = intent {
        updateLoadingState(true)
        fetchUserFollowersUseCase(userId)
            .onSuccess { followers ->
                reduce {
                    state.copy(
                        followers = followers.toImmutableList(),
                    )
                }
            }.onFailure {
                postSideEffect(FriendsSideEffect.ReportError(it))
            }.also {
                updateLoadingState(false)
            }
    }

    private fun fetchUserFollowings(userId: Int) = intent {
        updateLoadingState(true)
        fetchUserFollowingsUseCase(userId)
            .onSuccess { followers ->
                reduce {
                    state.copy(
                        followings = followers.toImmutableList(),
                    )
                }
            }.onFailure {
                postSideEffect(FriendsSideEffect.ReportError(it))
            }.also {
                updateLoadingState(false)
            }
    }

    private fun updateLoadingState(loading: Boolean) = intent {
        reduce {
            state.copy(
                isLoading = loading,
            )
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
