/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel.ranking

internal sealed class RankingSideEffect {
    class ReportError(val exception: Throwable) : RankingSideEffect()

    object NavigateToCreateProblem : RankingSideEffect()

    class ListPullUp(val currentTab: Int) : RankingSideEffect()
    class NavigateToExamDetail(val examId: Int) : RankingSideEffect()
}
