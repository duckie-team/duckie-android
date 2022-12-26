/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel.state

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.screen.BottomNavigationStep
import team.duckie.app.android.feature.ui.home.screen.HomeRecommendJumbotron
import team.duckie.app.android.feature.ui.home.screen.RecommendUserByTopic
import team.duckie.app.android.feature.ui.home.screen.TestMaker
import team.duckie.app.android.feature.ui.home.screen.TopicRecommendItem

data class HomeState(
    val step: BottomNavigationStep = BottomNavigationStep.HomeScreen,
    val selectedTabIndex: HomeStep = HomeStep.HomeRecommendScreen,

    val jumbotrons: PersistentList<HomeRecommendJumbotron> = persistentListOf(),
    val recommendTopics: PersistentList<TopicRecommendItem> = persistentListOf(),

    val recommendFollowing: PersistentList<RecommendUserByTopic> = persistentListOf(),
    val recommendFollowingTest: PersistentList<TestMaker> = persistentListOf(),
)
