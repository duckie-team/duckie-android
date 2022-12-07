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

package team.duckie.app.android.data.user.mapper

import team.duckie.app.android.data.user.model.UserData
import team.duckie.app.android.domain.user.model.User

internal fun UserData.toDomain() = User(
    id = id,
    nickName = nickName,
    accountEnabled = accountEnabled,
    profileUrl = profileUrl,
    tier = tier,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
    bannedAt = bannedAt,
)
