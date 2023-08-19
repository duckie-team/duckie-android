/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.viewmodel.home

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.recommendation.model.ExamType
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.home.constants.HomeStep

internal data class HomeState(
    val me: User? = null,

    val isError: Boolean = false,

    val isHomeRecommendLoading: Boolean = false,
    val isHomeRecommendFollowingExamLoading: Boolean = false,

    val isHomeRecommendFollowingExamRefreshLoading: Boolean = false,
    val isHomeRecommendPullRefreshLoading: Boolean = false,

    val homeSelectedIndex: HomeStep = HomeStep.HomeRecommendScreen,

    val jumbotrons: ImmutableList<HomeRecommendJumbotron> = skeletonJumbotrons,
    val recommendTopics: ImmutableList<RecommendTopic> = persistentListOf(),

    val isFollowingExist: Boolean = true,
    val recommendFollowing: ImmutableList<RecommendUserByTopic> = persistentListOf(),
) {
    /**
     * 팔로잉의 덕질고사 추천 피드 data class [RecommendExam]
     *
     * @param coverUrl 덕질고사 커버 이미지 url
     * @param title 덕질고사 제목
     * @param owner 덕질고사 만든이
     */
    @Immutable
    data class RecommendExam(
        val coverUrl: String,
        val title: String,
        val examId: Int,
        val owner: User,
    ) {
        /**
         * 덕질고사 만든 유저 정보
         * @param nickname 닉네임
         * @param profileImgUrl 프로필 이미지 url
         * @param favoriteTag 가장 관심 있는 태그의 이름
         * @param tier 덕질 티어
         */
        @Immutable
        data class User(
            val nickname: String,
            val profileImgUrl: String,
            val favoriteTag: String,
            val tier: String,
            val userId: Int,
        ) {
            /**
             * [User] 의 Empty Model 입니다.
             * 초기화 혹은 Skeleton UI 등에 필요한 Mock Data 로 쓰입니다.
             */
            companion object {
                fun empty() = User("", "", "", "", 0)
            }
        }

        /**
         * [RecommendExam] 의 Empty Model 입니다.
         * 초기화 혹은 Skeleton UI 등에 필요한 Mock Data 로 쓰입니다.
         */
        companion object {
            fun empty() = RecommendExam(
                "",
                "",
                0,
                User.empty(),
            )
        }
    }

    /**
     * 주제에 관련한 유저 추천 data class [RecommendUserByTopic]
     *
     * @param topic 덕질고사 주제
     * @param users 추천하는 유저들
     */
    @Immutable
    data class RecommendUserByTopic(
        val topic: String = "",
        val users: ImmutableList<User> = persistentListOf(),
    ) {
        /**
         * 추천되는 유저의 정보
         *
         * @param userId 유저 id
         * @param profileImgUrl 프로필 이미지 url
         * @param nickname 닉네임
         * @param favoriteTag 가장 관심 있는 태그의 이름
         * @param tier 덕질 티어
         * @param isFollowing 팔로잉 여부
         */
        @Immutable
        data class User(
            val userId: Int,
            val profileImgUrl: String,
            val nickname: String,
            val favoriteTag: String,
            val tier: String,
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
        val examId: Int,
        val coverUrl: String?,
        val title: String,
        val content: String,
        val buttonContent: String,
        val type: ExamType,
    )

    /**
     * 덕질고사 주제별 추천 피드 data class
     *
     * @param title 덕질고사 제목
     * @param tag 덕질고사 태그
     * @param items 추천하는 덕질고사 정보
     */
    @Immutable
    data class RecommendTopic(
        val title: String,
        val tag: String,
        val items: ImmutableList<Test>,
    ) {
        /**
         * 덕질고사 정보
         *
         * @param coverImg 덕질고사 커버 이미지 url
         * @param nickname 덕질고사 만든이 닉네임
         * @param title 덕질고사 제목
         * @param examineeNumber 덕질고사 응시자 수
         * @param recommendId 덕질고사 추천 id
         */
        @Immutable
        data class Test(
            val coverImg: String,
            val nickname: String,
            val title: String,
            val examId: Int,
            val examineeNumber: Int,
            val recommendId: Int,
        )
    }
}
