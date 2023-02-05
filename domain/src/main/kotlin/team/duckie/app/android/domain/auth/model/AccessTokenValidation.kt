/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.auth.model

data class AccessTokenValidation(
    val passed: Boolean,
    val userId: Int? = null,
) {
    val requireUserId get() = requireNotNull(userId) {
        "{passed=false} 일 때는 userId 가 not-null 일 수 없습니다."
    }

    companion object {
        val failure = AccessTokenValidation(passed = false)

        fun fromUserId(userId: Int): AccessTokenValidation {
            return AccessTokenValidation(passed = true, userId = userId)
        }
    }
}
