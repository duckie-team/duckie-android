/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.search.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.search.model.Search
import team.duckie.app.android.domain.search.repository.SearchRepository
import javax.inject.Inject

@Immutable
class GetSearchUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    suspend operator fun invoke(query: String, page: Int, type: SearchType): Result<Search> {
        return runCatching {
            repository.getSearch(query, page, type)
        }
    }
}
