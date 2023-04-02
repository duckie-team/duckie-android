/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.report.repository

import team.duckie.app.android.data.report.datasource.ReportRemoteDataSource
import team.duckie.app.android.domain.report.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportRemoteDataSource: ReportRemoteDataSource,
) : ReportRepository {

    override suspend fun report(examId: Int) {
        reportRemoteDataSource.report(examId = examId)
    }
}
