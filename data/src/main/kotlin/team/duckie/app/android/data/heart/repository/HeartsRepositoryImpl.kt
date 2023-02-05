/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.heart.repository

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import javax.inject.Inject
import team.duckie.app.android.data._datasource.bodyAsText
import team.duckie.app.android.data._exception.util.responseCatchingGet
import team.duckie.app.android.data._util.buildJson
import team.duckie.app.android.domain.heart.model.HeartsBody
import team.duckie.app.android.domain.heart.repository.HeartsRepository

class HeartsRepositoryImpl @Inject constructor(private val fuel: Fuel) : HeartsRepository {
    override suspend fun heart(examId: Int): Int {
        val (_, response) = fuel
            .post("/hearts")
            .body(
                body = buildJson {
                    "examId" withString "$examId"
                },
            ).awaitStringResponse()

        return responseCatchingGet(
            response.statusCode,
            "id",
            response.bodyAsText(),
        ).toInt()
    }

    override suspend fun unHeart(heartsBody: HeartsBody): Boolean {
        requireNotNull(heartsBody.heartId)
        val (_, response) = fuel
            .delete("/hearts")
            .body(
                body = buildJson {
                    "examId" withString "${heartsBody.examId}"
                    "heartId" withString "${heartsBody.heartId}"
                },
            ).awaitStringResponse()

        return responseCatchingGet(
            response.statusCode,
            "success",
            response.bodyAsText(),
        ).toBoolean()
    }
}
