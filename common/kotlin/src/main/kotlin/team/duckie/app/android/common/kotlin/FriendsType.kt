/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.kotlin

enum class FriendsType(
    val index: Int,
) {
    Follower(0),
    Following(1),
    ;

    companion object {
        fun fromIndex(index: Int): FriendsType {
            return values().first { it.index == index }
        }
    }
}
