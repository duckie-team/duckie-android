/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.search.datasource

import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.duckie.app.android.data._exception.util.responseCatchingFuel
import team.duckie.app.android.data.search.mapper.toDomain
import team.duckie.app.android.data.search.model.SearchData
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.search.model.Search
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val fuel: Fuel,
) : SearchRemoteDataSource {

    override suspend fun getSearch(
        query: String,
        page: Int,
        type: SearchType,
    ): Search = withContext(Dispatchers.IO) {
        val (_, response) = fuel.get(
            "/search",
            listOf(
                "query" to query,
                "page" to page,
                "type" to type.type,
            ),
        ).responseString()

        return@withContext responseCatchingFuel(
            response,
            SearchData::toDomain,
        )
    }
}
