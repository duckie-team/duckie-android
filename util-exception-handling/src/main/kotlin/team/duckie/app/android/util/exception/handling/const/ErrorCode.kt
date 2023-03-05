/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.exception.handling.const

object ErrorCode {
    /** 팔로잉이 존재하지 않을 때 발생하는 Error */
    const val FollowingNotFound = "FOLLOWING_NOT_FOUND"

    /** 팔로잉이 이미 존재할 때 발생하는 Error*/
    const val FollowingAlreadyExists = "FOLLOWING_ALREADY_EXISTS"
}
