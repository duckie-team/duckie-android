/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.home.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.heart.model.Heart
import team.duckie.app.android.domain.user.model.User

@Immutable
data class HomeFunding(
    val id: Int,
    val title: String,
    val thumbnailUrl: String,
    val totalProblemCount: Int,
    val problemCount: Int,
    val contributorCount: Int,
    val user: User?,
    val heart: Heart?,
) {
    val remainCount: Int
        get() {
            val temp = totalProblemCount - problemCount
            return if (temp < 0) 0 else temp
        }
}
