/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.heart.model

import androidx.compose.runtime.Immutable

@Immutable
data class HeartsBody(
    val examId: Int,
    val heartId: Int? = null,
)