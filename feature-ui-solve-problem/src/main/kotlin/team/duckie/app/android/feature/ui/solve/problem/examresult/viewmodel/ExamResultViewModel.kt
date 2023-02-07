/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.examresult.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ExamResultViewModel @Inject constructor() : ViewModel(),
    ContainerHost<ExamResultState, ExamResultSideEffect> {
    override val container: Container<ExamResultState, ExamResultSideEffect> = container(
        ExamResultState()
    )

    fun getReport() = intent {
        delay(1000L)
        reduce {
            state.copy(
                isReportLoading = false,
                reportUrl = "https://duckie-resource.s3.ap-northeast-2.amazonaws.com/exam/thumbnail/1673888692440",
            )
        }
    }
}
