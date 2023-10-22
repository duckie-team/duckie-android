/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.problem.repository

import team.duckie.app.android.data.problem.datasource.ProblemDataSource
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.problem.model.ProblemBody
import team.duckie.app.android.domain.problem.repository.ProblemRepository
import javax.inject.Inject

class ProblemRepositoryImpl @Inject constructor(
    private val problemDataSource: ProblemDataSource,
) : ProblemRepository {
    override suspend fun patchProblem(problemId: Int, status: String, isSample: Boolean): Problem {
        return problemDataSource.patchProblem(problemId, status, isSample)
    }

    override suspend fun postProblem(problemBody: ProblemBody): Problem {
        return problemDataSource.postProblem(problemBody)
    }
}
