/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.me

import team.duckie.app.android.domain.user.model.User

interface MeRepository {
    suspend fun getMe(): User

    suspend fun setMe(newMe: User)
}
