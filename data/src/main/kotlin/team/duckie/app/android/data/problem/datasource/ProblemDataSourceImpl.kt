/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.problem.datasource

import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data.exam.mapper.toDomain
import team.duckie.app.android.data.exam.model.ProblemData
import team.duckie.app.android.data.problem.model.PatchProblemBodyData
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.problem.model.ProblemBody
import toData
import javax.inject.Inject

class ProblemDataSourceImpl @Inject constructor() : ProblemDataSource {
    // https://www.notion.so/duckie-team/PATCH-problems-id-046950c590de43c5b478c3d8a16e84df?pvs=4
    override suspend fun patchProblem(problemId: Int, status: String, isSample: Boolean): Problem {
        val response = client.patch {
            url("/problems/$problemId")
            setBody(PatchProblemBodyData(status, isSample))
        }
        return responseCatching(
            response,
            ProblemData::toDomain,
        )
    }

    // https://www.notion.so/duckie-team/POST-problems-aa16551f9dee4f37b372bef73f468137?pvs=4
    override suspend fun postProblem(problemBody: ProblemBody): Problem {
        val response = client.post {
            url("/problems")
            setBody(problemBody.toData())
        }

        return responseCatching(
            response,
            ProblemData::toDomain,
        )
    }
}
