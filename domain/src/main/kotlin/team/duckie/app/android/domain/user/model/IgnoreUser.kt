/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.model

data class IgnoreUser(
    val id: Int,
    val nickName: String,
    val profileImageUrl: String,
    val duckPower: DuckPower?,
    val userBlock: UserBlock,
)
