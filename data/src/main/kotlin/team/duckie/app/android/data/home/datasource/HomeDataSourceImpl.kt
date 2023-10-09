/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.home.datasource

import HomeFundingsResponseData
import io.ktor.client.request.get
import io.ktor.client.request.url
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data.home.mapper.toDomain
import team.duckie.app.android.domain.home.model.HomeFunding
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor() : HomeDataSource {
    // https://www.notion.so/duckie-team/GET-home-funding-b1a282245a2446b6a500b986e3464cf6?pvs=4
    override suspend fun getFunding(): List<HomeFunding> {
        val response = client.get {
            url("/home/funding")
        }
        val result = responseCatching(
            response = response,
            parse = HomeFundingsResponseData::toDomain,
        )
        return result.upcomingExams
    }
}
