/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("TooGenericExceptionCaught")

package team.duckie.app.android.data.ranking.datasource

import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.duckie.app.android.data._exception.util.responseCatchingFuel
import team.duckie.app.android.data.exam.mapper.toDomain
import team.duckie.app.android.data.exam.model.ExamsData
import team.duckie.app.android.data.ranking.model.OrderType
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.data.user.model.UsersResponse
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.user.model.User
import javax.inject.Inject

class RankingRemoteDataSourceImpl @Inject constructor(
    private val fuel: Fuel,
) : RankingDataSource {
    override suspend fun getExamRankings(
        page: Int,
        order: OrderType,
        tagId: Int?,
    ): List<Exam> = withContext(Dispatchers.IO) {
        val (_, response) = fuel.get(
            "/ranking/exams",
            listOf(
                "page" to page,
                "order" to order.text,
                "tagId" to tagId,
            ),
        ).responseString()

        return@withContext responseCatchingFuel(
            response,
            ExamsData::toDomain,
        )
    }

    override suspend fun getUserRankings(
        page: Int,
    ): List<User> = withContext(Dispatchers.IO) {
        val (_, response) = fuel.get(
            "/ranking/users",
            listOf("page" to page),
        ).responseString()

        return@withContext responseCatchingFuel(
            response,
            UsersResponse::toDomain,
        )
    }
}
