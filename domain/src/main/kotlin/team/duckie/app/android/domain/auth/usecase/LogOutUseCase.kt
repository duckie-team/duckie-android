/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.auth.usecase

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import team.duckie.app.android.domain.device.repository.DeviceRepository
import team.duckie.app.android.domain.me.MeRepository
import javax.inject.Inject

@Immutable
class LogOutUseCase @Inject constructor(
    private val meRepository: MeRepository,
    private val deviceRepository: DeviceRepository,
) {
    /**
     * [deviceRepository] 까지 에러를 캐칭 할 경우 인터넷 환경이 없을 시에
     * 로그아웃을 하지 못하므로 이는 사용자 경험에 어긋나기에 에러 핸들링을 하지 않는다.
     *
     * TODO(limsaehyun) 추후 조치가 핋요함
     */
    suspend operator fun invoke(userId: Int): Result<Unit> {
        withContext(SupervisorJob()) {
            deviceRepository.unRegister(userId)
        }
        return runCatching {
            meRepository.clearMeToken()
        }
    }
}
