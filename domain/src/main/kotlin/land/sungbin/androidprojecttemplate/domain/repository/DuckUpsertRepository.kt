package land.sungbin.androidprojecttemplate.domain.repository

import land.sungbin.androidprojecttemplate.domain.model.Chat
import land.sungbin.androidprojecttemplate.domain.model.ChatRead
import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.model.Comment
import land.sungbin.androidprojecttemplate.domain.model.ContentStayTime
import land.sungbin.androidprojecttemplate.domain.model.DealReview
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.FeedScore
import land.sungbin.androidprojecttemplate.domain.model.Follow
import land.sungbin.androidprojecttemplate.domain.model.Heart
import land.sungbin.androidprojecttemplate.domain.model.Report
import land.sungbin.androidprojecttemplate.domain.model.SaleRequest
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.constraint.Review
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult

/**
 * 덕키 Firebase API upsert 결과를 나타냅니다.
 *
 * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 타입이 유효합니다.
 */
private typealias DuckUpsertResult = DuckApiResult<Nothing>

/**
 * Upsert 요청을 하는 API 들의 시그니처를 정의합니다.
 */
interface DuckUpsertRepository : DuckRepository {
    /**
     * [유저][User] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param user 정보를 생성하거나 업데이트할 [유저][User] 객체
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend fun upsertUser(
        user: User,
    ): DuckUpsertResult

    /**
     * [팔로우][Follow] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param follow 정보를 생성하거나 업데이트할 [팔로우][Follow] 객체
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend fun upsertFollow(
        follow: Follow,
    ): DuckUpsertResult

    /**
     * [채팅방][ChatRoom] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param chatRoom 정보를 생성하거나 업데이트할 [채팅방][ChatRoom] 객체
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend fun upsertChatRoom(
        chatRoom: ChatRoom,
    ): DuckUpsertResult

    /**
     * [채팅][Chat] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param chat 정보를 생성하거나 업데이트할 [채팅][Chat] 객체
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend fun upsertChat(
        chat: Chat,
    ): DuckUpsertResult

    /**
     * [피드][Feed] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param feed 정보를 생성하거나 업데이트할 [피드][Feed] 객체
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend fun upsertFeed(
        feed: Feed,
    ): DuckUpsertResult

    /**
     * [채팅 확인][ChatRead] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param chatRead 정보를 생성하거나 업데이트할 [채팅 확인][ChatRead] 객체
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    @Unsupported
    suspend fun upsertChatRead(
        chatRead: ChatRead,
    ): DuckUpsertResult

    /**
     * [좋아요][Heart] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param heart 정보를 생성하거나 업데이트할 [좋아요][Heart] 객체
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend fun upsertHeart(
        heart: Heart,
    ): DuckUpsertResult

    /**
     * [댓글][Comment] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param comment 정보를 생성하거나 업데이트할 [댓글][Comment] 객체
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend fun upsertComment(
        comment: Comment,
    ): DuckUpsertResult

    /**
     * [리뷰][Review] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param review 정보를 생성하거나 업데이트할 [리뷰][DealReview] 객체
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    // See: https://github.com/duckie-team/duckie-app-mvp/issues/32
    suspend fun upsertReview(
        review: DealReview,
    ): DuckUpsertResult

    /**
     * [판매 요청][SaleRequest] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param saleRequest 정보를 생성하거나 업데이트할 [판매 요청][SaleRequest] 객체
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend fun upsertSaleRequest(
        saleRequest: SaleRequest,
    ): DuckUpsertResult

    /**
     * [신고][Report] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param report 정보를 생성하거나 업데이트할 [신고][Report] 객체
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    // TODO: 신고 조회는 덕키 앱에서 사용되지 않으므로 추후 덕키 관리 서비스에서 구현해야 합니다.
    suspend fun upsertReport(
        report: Report,
    ): DuckUpsertResult

    /**
     * [피드 선호도][FeedScore] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param feedScore 정보를 생성하거나 업데이트할 [피드 선호도][FeedScore] 객체
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    @Unsupported
    suspend fun upsertFeedScore(
        feedScore: FeedScore,
    ): DuckUpsertResult

    /**
     * [컨텐츠 별 머문 시간][ContentStayTime] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param contentStayTime 정보를 생성하거나 업데이트할 [컨텐츠 별 머문 시간][ContentStayTime] 객체
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    @Unsupported
    suspend fun upsertContentStayTime(
        contentStayTime: ContentStayTime,
    ): DuckUpsertResult
}
