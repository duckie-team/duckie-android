/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.search.datasource

import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.search.model.Search

interface SearchRemoteDataSource {

    suspend fun getSearch(
        query: String,
        page: Int,
        type: SearchType,
    ): Search
}
