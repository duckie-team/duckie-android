/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.datasource

import team.duckie.app.android.data.exam.model.ExamInfoEntity

interface ExamInfoDataSource {
    suspend fun getFavoriteExams(): List<ExamInfoEntity>

    suspend fun getMadeExams(): List<ExamInfoEntity>

    suspend fun getSolvedExams(): List<ExamInfoEntity>
}
