/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.heart.repository

import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.duckie.app.android.data._datasource.bodyAsText
import team.duckie.app.android.data._exception.util.responseCatchingFuel
import team.duckie.app.android.data._exception.util.responseCatchingGet
import team.duckie.app.android.data._util.buildJson
import team.duckie.app.android.data.heart.mapper.toDomain
import team.duckie.app.android.data.heart.model.HeartData
import team.duckie.app.android.domain.heart.model.Heart
import team.duckie.app.android.domain.heart.repository.HeartRepository
import javax.inject.Inject

class HeartRepositoryImpl @Inject constructor(private val fuel: Fuel) : HeartRepository {
    override suspend fun postHeart(examId: Int): Heart = withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .post("/hearts")
            .body(
                body = buildJson {
                    "examId" withInt examId
                },
            ).responseString()

        return@withContext responseCatchingFuel(
            response,
            HeartData::toDomain,
        )
    }

    override suspend fun deleteHeart(heartId: Int): Boolean = withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .delete("/hearts/$heartId")
            .responseString()

        return@withContext responseCatchingGet(
            response.statusCode,
            "success",
            response.bodyAsText(),
        ).toBoolean()
    }
}
