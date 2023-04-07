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

// TODO(limsaehyun) : 검색의 관심사 분리를 위햐 제거 해야 함
@Immutable
class GetSearchUseCase(private val repository: SearchRepository) {
    suspend operator fun invoke(query: String, page: Int, type: SearchType): Result<Search> {
        return runCatching {
            repository.getSearch(query, page, type)
        }
    }
}
