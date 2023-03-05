/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.model

import team.duckie.app.android.util.kotlin.exception.duckieResponseFieldNpe

enum class UserStatus(val value: String) {
    NEW("NEW"),
    READY("READY"),
    BANNED("BANNED"),
}

val String.toUserAuthStatus: UserStatus
    get() = when (this) {
        UserStatus.NEW.value -> UserStatus.NEW
        UserStatus.READY.value -> UserStatus.READY
        UserStatus.BANNED.value -> UserStatus.BANNED
        else -> duckieResponseFieldNpe("Not found UserAuthStatus enum type")
    }
