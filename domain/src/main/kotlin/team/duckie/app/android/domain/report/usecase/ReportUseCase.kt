/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.report.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.report.repository.ReportRepository
import javax.inject.Inject

@Immutable
class ReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
) {

    suspend operator fun invoke(examId: Int) = runCatching {
        reportRepository.report(examId = examId)
    }
}
