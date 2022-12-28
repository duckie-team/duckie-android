/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.domain.user.model

import androidx.compose.runtime.Immutable
import java.util.Date
import team.duckie.app.android.domain.user.constant.DuckieTier
import team.duckie.app.android.util.kotlin.OutOfDateApi

@OutOfDateApi
@Immutable
data class User(
    val id: Int,
    val nickname: String,
    val accountEnabled: Boolean,
    val profileUrl: String,
    val tier: DuckieTier,
    val createdAt: Date,
    val updatedAt: Date,
    val deletedAt: Date? = null,
    val bannedAt: Date? = null,
)
