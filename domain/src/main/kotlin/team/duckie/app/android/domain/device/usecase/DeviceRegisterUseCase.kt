/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.device.usecase

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import team.duckie.app.android.domain.device.repository.DeviceRepository
import team.duckie.app.android.common.kotlin.ExperimentalApi

@Immutable
class DeviceRegisterUseCase @Inject constructor(
    private val repository: DeviceRepository,
) {
    @ExperimentalApi
    suspend fun invoke(fcmToken: String): Result<Unit> {
        return runCatching {
            repository.register(fcmToken)
        }
    }
}
