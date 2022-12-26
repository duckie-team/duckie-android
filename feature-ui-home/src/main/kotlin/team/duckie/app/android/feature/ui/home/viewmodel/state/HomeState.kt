/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.screen.BottomNavigationStep

data class HomeState(
    val step: BottomNavigationStep = BottomNavigationStep.HomeScreen,
    val selectedTabIndex: HomeStep = HomeStep.HomeRecommendScreen,

    val jumbotrons: PersistentList<HomeRecommendJumbotron> = persistentListOf(),
    val recommendTopics: PersistentList<TopicRecommendItem> = persistentListOf(),

    val recommendFollowing: PersistentList<RecommendUserByTopic> = persistentListOf(),
    val recommendFollowingTest: PersistentList<TestMaker> = persistentListOf(),
) {
    data class TestMaker(
        val coverUrl: String,
        val title: String,
        val examineeNumber: Int,
        val createAt: String,
        val owner: User,
    ) {
        data class User(
            val name: String,
            val profile: String,
        )
    }

    data class RecommendUserByTopic(
        val topic: String,
        val users: ImmutableList<RecommendUser>,
    )

    data class RecommendUser(
        val userId: Int,
        val profile: String,
        val name: String,
        val examineeNumber: Int,
        val createAt: String,
    )

    data class HomeRecommendJumbotron(
        val image: String,
        val title: String,
        val content: String,
        val buttonContent: String,
    )

    data class TopicRecommendItem(
        val title: String,
        val tag: String,
        val items: PersistentList<DuckTest>,
    ) {
        data class DuckTest(
            val coverImg: String,
            val nickname: String,
            val title: String,
            val examineeNumber: Int,
            val recommendId: Int,
        )
    }
}
