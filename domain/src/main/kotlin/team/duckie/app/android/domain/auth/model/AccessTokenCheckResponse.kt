/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.auth.model

import androidx.compose.runtime.Immutable

@Immutable
data class AccessTokenCheckResponse(
    val userId: Int,
    val type: String,
)
