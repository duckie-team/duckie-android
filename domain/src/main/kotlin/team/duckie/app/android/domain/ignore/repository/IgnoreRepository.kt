/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.ignore.repository

import androidx.compose.runtime.Immutable

@Immutable
interface IgnoreRepository {
    suspend fun ignoreUser(targetId: Int)
    suspend fun cancelIgnoreUser(targetId: Int)
}
