/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.user.model.KakaoUser

@Immutable
interface KakaoLoginRepository {
    suspend fun login(): KakaoUser
}
