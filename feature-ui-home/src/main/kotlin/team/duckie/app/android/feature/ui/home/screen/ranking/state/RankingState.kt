/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.ranking.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.ui.home.screen.ranking.dummy.skeletonExamineeItems
import team.duckie.app.android.feature.ui.home.screen.ranking.dummy.skeletonTags
import team.duckie.app.android.util.kotlin.fastMap

internal data class RankingState(
    val isLoading: Boolean = true,
    val selectedTab: Int = 0,
    val examinees: ImmutableList<User> = skeletonExamineeItems,
    val examTags: ImmutableList<Tag> = skeletonTags,
    val selectedExamOrder: Int = 0,
    val tagSelections: ImmutableList<Boolean> = examTags.fastMap { false }.toImmutableList(),
)
