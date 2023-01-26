/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.tag.model.Tag

@Immutable
data class DuckPower(
    val id: Int,
    val tier: String,
    val tag: Tag,
)
