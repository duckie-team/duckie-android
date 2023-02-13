/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.follow.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.follow.model.FollowBody
import javax.inject.Inject
import team.duckie.app.android.domain.follow.repository.FollowRepository

@Immutable
class FollowUseCase @Inject constructor(
    private val followRepository: FollowRepository,
) {
    suspend operator fun invoke(followBody: FollowBody, isFollowing: Boolean) = runCatching {
        if (isFollowing) {
            followRepository.unFollow(followBody)
        } else {
            followRepository.follow(followBody)
        }
    }
}
