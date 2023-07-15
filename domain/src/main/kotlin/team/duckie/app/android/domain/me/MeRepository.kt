/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.me

import team.duckie.app.android.domain.user.model.User

// TODO(riflockle7): 추후 앱 크래시 데이터 복구용으로 네이밍 변경 필요
interface MeRepository {
    suspend fun getMe(): User

    suspend fun setMe(newMe: User)

    suspend fun clearMeToken()

    suspend fun getIsStage(): Boolean
}
