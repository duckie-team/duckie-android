/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.heart.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.heart.model.Heart

@Immutable
interface HeartRepository {
    suspend fun postHeart(examId: Int): Heart

    suspend fun deleteHeart(heartId: Int): Boolean
}
