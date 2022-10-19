package land.sungbin.androidprojecttemplate.domain.repository

import land.sungbin.androidprojecttemplate.domain.model.Chat
import land.sungbin.androidprojecttemplate.domain.model.ChatRead
import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.model.Comment
import land.sungbin.androidprojecttemplate.domain.model.ContentStayTime
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.FeedScore
import land.sungbin.androidprojecttemplate.domain.model.Follow
import land.sungbin.androidprojecttemplate.domain.model.Heart
import land.sungbin.androidprojecttemplate.domain.model.Report
import land.sungbin.androidprojecttemplate.domain.model.SaleRequest
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.constraint.Review

interface DuckFetchRepository {
    /**
     * 주어진 아이디로부터 [유저][User] 정보를 조회합니다.
     *
     * 등록된 정보가 있다면 해당 값을 반환하고, 그렇지 않다면
     * null 을 반환합니다.
     *
     * @param id 조회할 [유저의 아이디][User.nickname]
     */
    suspend fun fetchUser(
        id: String,
    ): User?

    /**
     * [팔로우][Follow] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param follow 정보를 생성하거나 업데이트할 [팔로우][Follow] 객체
     */
    suspend fun fetchFollow(
        follow: Follow,
    )

    /**
     * [채팅방][ChatRoom] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param chatRoom 정보를 생성하거나 업데이트할 [채팅방][ChatRoom] 객체
     */
    suspend fun fetchChatRoom(
        chatRoom: ChatRoom,
    )

    /**
     * [채팅][Chat] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param chat 정보를 생성하거나 업데이트할 [채팅][Chat] 객체
     */
    suspend fun fetchChat(
        chat: Chat,
    )

    /**
     * [피드][Feed] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param feed 정보를 생성하거나 업데이트할 [피드][Feed] 객체
     */
    suspend fun fetchFeed(
        feed: Feed,
    )

    /**
     * [채팅 확인][ChatRead] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param chatRead 정보를 생성하거나 업데이트할 [채팅 확인][ChatRead] 객체
     */
    suspend fun fetchChatRead(
        chatRead: ChatRead,
    )

    /**
     * [좋아요][Heart] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param heart 정보를 생성하거나 업데이트할 [좋아요][Heart] 객체
     */
    suspend fun fetchHeart(
        heart: Heart,
    )

    /**
     * [댓글][Comment] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param comment 정보를 생성하거나 업데이트할 [댓글][Comment] 객체
     */
    suspend fun fetchComment(
        comment: Comment,
    )

    /**
     * [리뷰][Review] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param review 정보를 생성하거나 업데이트할 [리뷰][Review] 객체
     */
    suspend fun fetchReview(
        review: Review,
    )

    /**
     * [판매 요청][SaleRequest] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param saleRequest 정보를 생성하거나 업데이트할 [판매 요청][SaleRequest] 객체
     */
    suspend fun fetchSaleRequest(
        saleRequest: SaleRequest,
    )

    /**
     * [신고][Report] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param report 정보를 생성하거나 업데이트할 [신고][Report] 객체
     */
    suspend fun fetchReport(
        report: Report,
    )

    /**
     * [피드 선호도][ChatRoom] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param feedScore 정보를 생성하거나 업데이트할 [피드 선호도][FeedScore] 객체
     */
    suspend fun fetchFeedScore(
        feedScore: FeedScore,
    )

    /**
     * [컨텐츠 별 머문 시간][ContentStayTime] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param contentStayTime 정보를 생성하거나 업데이트할 [컨텐츠 별 머문 시간][ContentStayTime] 객체
     */
    suspend fun fetchTime(
        contentStayTime: ContentStayTime,
    )
}
