/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.device.repository

import androidx.compose.runtime.Immutable

@Immutable
interface DeviceRepository {
    suspend fun register(fcmToken: String)
}
