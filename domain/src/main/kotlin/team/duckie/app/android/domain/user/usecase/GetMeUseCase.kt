/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.me.MeRepository
import team.duckie.app.android.domain.user.model.User
import javax.inject.Inject

// TODO(riflockle7): 추후 앱 크래시 데이터 복구용으로 네이밍 변경 필요
@Immutable
class GetMeUseCase @Inject constructor(
    private val repository: MeRepository,
) {
    suspend operator fun invoke(): Result<User> {
        return runCatching { repository.getMe() }
    }
}
