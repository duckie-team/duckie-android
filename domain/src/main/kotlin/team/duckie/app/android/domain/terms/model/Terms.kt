/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.terms.model

import androidx.compose.runtime.Immutable
import java.util.Date

@Immutable
data class Terms(
    val id: Int,
    val condition: String,
    val version: String,
    val createdAt: Date,
)
