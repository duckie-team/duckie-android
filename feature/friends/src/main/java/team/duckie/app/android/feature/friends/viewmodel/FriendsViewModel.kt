/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.friends.viewmodel

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
import team.duckie.app.android.feature.friends.viewmodel.sideeffect.FriendsSideEffect
import team.duckie.app.android.feature.friends.viewmodel.state.FriendsState
import team.duckie.app.android.common.kotlin.FriendsType
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.common.android.ui.const.Extras
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
        initState()
        initNickName()
        initFriendType()
    }

    internal fun initState() = intent {
        val userId = savedStateHandle.getStateFlow(Extras.UserId, 0).value
        startLoadingState()

        getMeUseCase()
            .onSuccess { me ->
                val myId = me.id
                val isMine = userId == myId

                reduce { state.copy(me = me, isMine = isMine) }

                val targetId = if (isMine) myId else userId
                fetchUserFollowers(targetId)
                fetchUserFollowings(targetId)
            }
            .onFailure {
                reduce { state.copy(isError = true, isLoading = false) }
                postSideEffect(FriendsSideEffect.ReportError(it))
            }
    }

    private fun initFriendType() = intent {
        val friendType: FriendsType =
            savedStateHandle.getStateFlow(Extras.FriendType, 0).value.let { friendTypeIndex ->
                FriendsType.fromIndex(friendTypeIndex)
            }

        reduce { state.copy(friendType = friendType) }
    }

    private fun initNickName() = intent {
        val nickname = savedStateHandle.getStateFlow(Extras.ProfileNickName, "").value

        reduce { state.copy(targetName = nickname) }
    }

    private fun fetchUserFollowers(userId: Int) = intent {
        fetchUserFollowersUseCase(userId)
            .onSuccess { followers ->
                reduce {
                    state.copy(
                        isLoading = false,
                        followers = followers.fastMap(User::toUiModel).toImmutableList(),
                    )
                }
            }.onFailure {
                reduce { state.copy(isError = true, isLoading = false) }
                postSideEffect(FriendsSideEffect.ReportError(it))
            }
    }

    private fun fetchUserFollowings(userId: Int) = intent {
        fetchUserFollowingsUseCase(userId)
            .onSuccess { followers ->
                reduce {
                    state.copy(
                        isLoading = false,
                        followings = followers.fastMap(User::toUiModel).toImmutableList(),
                    )
                }
            }.onFailure {
                reduce { state.copy(isError = true, isLoading = false) }
                postSideEffect(FriendsSideEffect.ReportError(it))
            }
    }

    private fun startLoadingState() = intent {
        reduce {
            state.copy(
                isError = false,
                isLoading = true,
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
                            followers = state.followers.changeFollowingState(userId, isFollowing),
                        )
                    }

                    FriendsType.Following -> {
                        state.copy(
                            followings = state.followings.changeFollowingState(userId, isFollowing),
                        )
                    }
                }
            }
        }.onFailure { exception ->
            postSideEffect(FriendsSideEffect.ReportError(exception))
        }
    }

    fun navigateToUserProfile(userId: Int) = intent {
        postSideEffect(FriendsSideEffect.NavigateToUserProfile(userId))
    }

    private fun List<FriendsState.Friend>.changeFollowingState(
        filiterUserId: Int,
        isFollowing: Boolean,
    ): ImmutableList<FriendsState.Friend> {
        return map { user ->
            if (user.userId == filiterUserId) {
                user.copy(isFollowing = isFollowing)
            } else {
                user
            }
        }.toImmutableList()
    }
}
