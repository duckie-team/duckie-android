/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel.ranking

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.util.kotlin.copy
import team.duckie.app.android.util.kotlin.fastMap

internal data class RankingState(
    val isTagLoading: Boolean = true,
    val isPagingDataLoading: Boolean = true,
    val selectedTab: Int = 0,
    val examTags: ImmutableList<Tag> = skeletonTags,
    val selectedExamOrder: Int = 0,
    val tagSelections: ImmutableList<Boolean> = examTags
        .fastMap { false }
        .copy { add(0, true) }
        .toImmutableList(),
)

internal enum class ExamRankingOrder(val index: Int) {
    SolvedCount(0),
    AnswerRate(1),
    ;

    companion object {
        fun from(value: Int) = ExamRankingOrder.values().first { it.index == value }
    }
}

internal fun ImmutableList<Tag>.addAllTag() = this.copy { add(0, Tag(-1, "전체")) }
internal fun ImmutableList<Boolean>.addAllSelection() = this.copy { add(0, true) }
