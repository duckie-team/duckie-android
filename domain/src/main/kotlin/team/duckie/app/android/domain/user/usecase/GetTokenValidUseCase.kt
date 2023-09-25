/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.me.MeRepository
import javax.inject.Inject

@Immutable
class GetTokenValidUseCase @Inject constructor(
    private val meRepository: MeRepository,
) {

    suspend operator fun invoke() = runCatching {
        meRepository.getTokenValid()
    }
}
