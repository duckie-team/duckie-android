/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.problem.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.problem.model.ProblemBody

@Immutable
interface ProblemRepository {
    suspend fun patchProblem(problemId: Int, status: String, isSample: Boolean): Problem

    suspend fun postProblem(problemBody: ProblemBody): Problem
}
