package land.sungbin.androidprojecttemplate.domain.repository

import land.sungbin.androidprojecttemplate.domain.exception.DuckApiException
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

/**
 * 덕키 Firebase API 사용 결과를 나타냅니다.
 * 결과 처리를 쉽게하기 위해 [Result] 클래스를 이용합니다.
 *
 * Firebase API Result 는 [Unit] or [DuckApiException] 으로 나타나므로
 * `Result<Unit>` 타입이 유효합니다.
 */
private typealias DuckFirebaseResult = Result<Unit>

interface DuckRepository {
    /**
     * [유저][User] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param user 정보를 생성하거나 업데이트할 [유저][User] 객체
     */
    suspend fun createOrUpdateUser(
        user: User,
    ): DuckFirebaseResult

    /**
     * [팔로우][Follow] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param follow 정보를 생성하거나 업데이트할 [팔로우][Follow] 객체
     */
    suspend fun createOrUpdateFollow(
        follow: Follow,
    ): DuckFirebaseResult

    /**
     * [채팅방][ChatRoom] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param chatRoom 정보를 생성하거나 업데이트할 [채팅방][ChatRoom] 객체
     */
    suspend fun createOrUpdateChatRoom(
        chatRoom: ChatRoom,
    ): DuckFirebaseResult

    /**
     * [채팅][Chat] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param chat 정보를 생성하거나 업데이트할 [채팅][Chat] 객체
     */
    suspend fun createOrUpdateChat(
        chat: Chat,
    ): DuckFirebaseResult

    /**
     * [피드][Feed] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param feed 정보를 생성하거나 업데이트할 [피드][Feed] 객체
     */
    suspend fun createOrUpdateFeed(
        feed: Feed,
    ): DuckFirebaseResult

    /**
     * [채팅 확인][ChatRead] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param chatRead 정보를 생성하거나 업데이트할 [채팅 확인][ChatRead] 객체
     */
    suspend fun createOrUpdateChatRead(
        chatRead: ChatRead,
    ): DuckFirebaseResult

    /**
     * [좋아요][Heart] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param heart 정보를 생성하거나 업데이트할 [좋아요][Heart] 객체
     */
    suspend fun createOrUpdateHeart(
        heart: Heart,
    ): DuckFirebaseResult

    /**
     * [댓글][Comment] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param comment 정보를 생성하거나 업데이트할 [댓글][Comment] 객체
     */
    suspend fun createOrUpdateComment(
        comment: Comment,
    ): DuckFirebaseResult

    /**
     * [리뷰][Review] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param review 정보를 생성하거나 업데이트할 [리뷰][Review] 객체
     */
    suspend fun createOrUpdateReview(
        review: Review,
    ): DuckFirebaseResult

    /**
     * [판매 요청][SaleRequest] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param saleRequest 정보를 생성하거나 업데이트할 [판매 요청][SaleRequest] 객체
     */
    suspend fun createOrUpdateSaleRequest(
        saleRequest: SaleRequest,
    ): DuckFirebaseResult

    /**
     * [신고][Report] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param report 정보를 생성하거나 업데이트할 [신고][Report] 객체
     */
    suspend fun createOrUpdateReport(
        report: Report,
    ): DuckFirebaseResult

    /**
     * [피드 선호도][ChatRoom] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param feedScore 정보를 생성하거나 업데이트할 [피드 선호도][FeedScore] 객체
     */
    suspend fun createOrUpdateFeedScore(
        feedScore: FeedScore,
    ): DuckFirebaseResult

    /**
     * [컨텐츠 별 머문 시간][ContentStayTime] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param contentStayTime 정보를 생성하거나 업데이트할 [컨텐츠 별 머문 시간][ContentStayTime] 객체
     */
    suspend fun createOrUpdateTime(
        contentStayTime: ContentStayTime,
    ): DuckFirebaseResult
}
