/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.model

import java.util.Date

data class User(
    val id: Int,
    val nickName: String,
    val accountEnabled: Boolean,
    val profileUrl: String,
    val tier: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val deletedAt: Date? = null,
    val bannedAt: Date? = null,
)
