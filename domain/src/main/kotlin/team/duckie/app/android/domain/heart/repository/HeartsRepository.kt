/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.heart.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.heart.model.HeartsBody

@Immutable
interface HeartsRepository {
    suspend fun postHeart(examId: Int): Int

    suspend fun deleteHeart(heartsBody: HeartsBody): Boolean
}
