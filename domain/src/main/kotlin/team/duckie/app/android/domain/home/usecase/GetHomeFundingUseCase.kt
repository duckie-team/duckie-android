/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.home.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.home.model.HomeFunding
import team.duckie.app.android.domain.home.repository.HomeRepository
import javax.inject.Inject

@Immutable
class GetHomeFundingUseCase @Inject constructor(
    private val repository: HomeRepository,
) {
    suspend operator fun invoke(): Result<List<HomeFunding>> {
        return runCatching {
            repository.getFunding()
        }
    }
}
