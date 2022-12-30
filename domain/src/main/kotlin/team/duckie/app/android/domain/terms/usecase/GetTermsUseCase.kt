/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.terms.usecase

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import team.duckie.app.android.domain.terms.model.Terms
import team.duckie.app.android.domain.terms.repository.TermsRepository

@Immutable
class GetTermsUseCase @Inject constructor(
    private val repository: TermsRepository,
) {
    suspend fun invoke(version: String): Result<Terms> {
        return runCatching {
            repository.get(version)
        }
    }
}
