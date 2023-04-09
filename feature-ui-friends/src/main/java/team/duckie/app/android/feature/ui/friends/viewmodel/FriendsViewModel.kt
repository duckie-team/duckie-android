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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.follow.model.FollowBody
import team.duckie.app.android.domain.follow.usecase.FollowUseCase
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.usecase.FetchUserFollowersUseCase
import team.duckie.app.android.domain.user.usecase.FetchUserFollowingsUseCase
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.ui.friends.viewmodel.sideeffect.FriendsSideEffect
import team.duckie.app.android.feature.ui.friends.viewmodel.state.FriendsState
import team.duckie.app.android.util.kotlin.FriendsType
import team.duckie.app.android.util.kotlin.copy
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.ui.const.Extras
import javax.inject.Inject

@HiltViewModel
internal class FriendsViewModel @Inject constructor(
    private val followUseCase: FollowUseCase,
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

    private fun fetchUserFollowers(userId: Int) = intent {
        updateLoadingState(true)
        fetchUserFollowersUseCase(userId)
            .onSuccess { followers ->
                reduce {
                    state.copy(
                        followers = followers.fastMap(User::toUiModel).toImmutableList(),
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
                        followings = followers.fastMap(User::toUiModel).toImmutableList(),
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

    fun followUser(userId: Int, isFollowing: Boolean, type: FriendsType) = intent {
        followUseCase(
            followBody = FollowBody(
                followingId = userId,
            ),
            isFollowing = isFollowing,
        ).onSuccess {
            reduce {
                when (type) {
                    FriendsType.Follower -> {
                        state.copy(
                            followers = changeFollowingState(state.followers, userId, isFollowing),
                        )
                    }

                    FriendsType.Following -> {
                        state.copy(
                            followings = changeFollowingState(
                                state.followings,
                                userId,
                                isFollowing,
                            ),
                        )
                    }
                }
            }
        }.onFailure { exception ->
            postSideEffect(FriendsSideEffect.ReportError(exception))
        }
    }

    private fun changeFollowingState(
        users: List<FriendsState.Friend>,
        filiterUserId: Int,
        isFollowing: Boolean,
    ): ImmutableList<FriendsState.Friend> {
        return users.map { user ->
            if (user.userId == filiterUserId) {
                user.copy(isFollowing = isFollowing)
            } else {
                user
            }
        }.toImmutableList()
    }
}
