/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.recommendation.model.JumbotronType
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.constants.BottomNavigationStep

internal data class HomeState(
    val isHomeLoading: Boolean = false,

    val step: BottomNavigationStep = BottomNavigationStep.HomeScreen,
    val homeSelectedIndex: HomeStep = HomeStep.HomeRecommendScreen,

    val jumbotrons: ImmutableList<HomeRecommendJumbotron> = persistentListOf(),
    val recommendTopics: ImmutableList<RecommendTopic> = persistentListOf(),

    val recommendFollowing: ImmutableList<RecommendUserByTopic> = persistentListOf(),
    val recommendFollowingTest: ImmutableList<FollowingTest> = persistentListOf(),
) {
    /**
     * 팔로잉의 덕질고사 추천 피드 data class [FollowingTest]
     *
     * @param coverUrl 덕질고사 커버 이미지 url
     * @param title 덕질고사 제목
     * @param examineeNumber 덕질고사 응시자 정보
     * @param createAt 덕질고사가 만들어진 날짜
     * @param owner 덕질고사 제작자 정보
     */
    data class FollowingTest(
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

    /**
     * 주제에 관련한 유저 추천 data class [RecommendUserByTopic]
     *
     * @param topic 덕질고사 주제
     * @param users 추천하는 유저들
     */
    data class RecommendUserByTopic(
        val topic: String = "",
        val users: ImmutableList<User> = persistentListOf(),
    ) {
        data class User(
            val userId: Int,
            val profile: String,
            val name: String,
            val examineeNumber: Int,
            val createAt: String,
            val isFollowing: Boolean = false,
        )
    }

    /**
     * 덕질고사 jumbotron 추천 피드 data class
     *
     * @param coverUrl 덕질고사 커버 이미지 url
     * @param title 덕질고사 제목
     * @param content 덕질고사 설명
     * @param buttonContent 시작 버튼 content
     */
    @Immutable
    data class HomeRecommendJumbotron(
        val id: Int,
        val coverUrl: String?,
        val title: String,
        val content: String,
        val buttonContent: String,
        val type: JumbotronType,
    )

    /**
     * 덕질고사 주제별 추천 피드 data class
     *
     * @param title 덕질고사 제목
     * @param tag 덕질고사 태그
     * @param items 추천하는 덕질고사 정보
     */
    data class RecommendTopic(
        val title: String,
        val tag: String,
        val items: ImmutableList<Test>,
    ) {
        data class Test(
            val coverImg: String,
            val nickname: String,
            val title: String,
            val examineeNumber: Int,
            val recommendId: Int,
        )
    }
}
