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
import team.duckie.app.android.data._util.buildJson
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.domain.heart.model.HeartBody
import team.duckie.app.android.domain.heart.repository.HeartsRepository
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import javax.inject.Inject

class HeartsRepositoryImpl @Inject constructor(private val fuel: Fuel) : HeartsRepository {
    override suspend fun heart(examId: Int): Int = withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .post("/hearts")
            .body(
                body = buildJson {
                    "examId" withString "$examId"
                },
            ).responseString()

        val jsonResponse = response.bodyAsText().toStringJsonMap()
        // TODO(riflockle7): 에러 핸들링 처리 필요
        return@withContext jsonResponse["id"]?.toInt() ?: duckieResponseFieldNpe("id")
    }

    override suspend fun unHeart(heartsBody: HeartBody): Boolean = withContext(Dispatchers.IO) {
        requireNotNull(heartsBody.heartId)
        val (_, response) = fuel
            .delete("/hearts")
            .body(
                body = buildJson {
                    "examId" withString "${heartsBody.examId}"
                    "heartId" withString "${heartsBody.heartId}"
                },
            ).responseString()

        val jsonResponse = response.bodyAsText().toStringJsonMap()
        // TODO(riflockle7): 에러 핸들링 처리 필요
        return@withContext jsonResponse["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
    }
}
