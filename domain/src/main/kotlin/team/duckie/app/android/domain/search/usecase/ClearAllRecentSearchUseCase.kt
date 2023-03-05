/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.search.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.search.repository.SearchRepository
import javax.inject.Inject

@Immutable
class ClearAllRecentSearchUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    operator fun invoke() = runCatching {
        repository.clearAllRecentSearch()
    }
}
