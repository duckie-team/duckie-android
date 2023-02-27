/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.search.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.search.repository.SearchRepository
import javax.inject.Inject

/**
 * 태그와 관련된 검색 결과를 가져오기 위한 유즈케이스
 */
@Immutable
class SearchTagsUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    operator fun invoke(tag: String) = runCatching {
        repository.searchTags(
            query = tag,
        )
    }
}
