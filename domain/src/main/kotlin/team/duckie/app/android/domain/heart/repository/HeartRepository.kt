/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.heart.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.heart.model.Hearts
import team.duckie.app.android.domain.heart.model.HeartsBody

@Immutable
interface HeartRepository {
    suspend fun postHeart(examId: Int): Hearts

    suspend fun deleteHeart(heartBody: HeartsBody): Boolean
}
