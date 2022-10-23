package land.sungbin.androidprojecttemplate.domain.repository

import land.sungbin.androidprojecttemplate.domain.model.Chat
import land.sungbin.androidprojecttemplate.domain.model.ChatRead
import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.model.Comment
import land.sungbin.androidprojecttemplate.domain.model.DealReview
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.Follow
import land.sungbin.androidprojecttemplate.domain.model.Heart
import land.sungbin.androidprojecttemplate.domain.model.SaleRequest
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.constraint.HeartTarget
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckFetchResult

/**
 * Fetch 요청을 하는 API 들의 시그니처를 정의합니다.
 */
interface FetchRepository : DuckRepository {
    /**
     * 주어진 아이디로부터 [유저][User] 정보를 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param id 조회할 [유저의 아이디][User.nickname]
     * @return 조회된 [유저][User] 정보를 담은 [fetch 결과][DuckFetchResult]
     */
    suspend fun fetchUser(
        @PK id: String,
    ): DuckFetchResult<User>

    /**
     * 주어진 아이디로부터 [팔로우][Follow] 정보를 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param userId 조회할 [유저의 아이디][User.nickname]
     * @return 조회된 [팔로우][Follow] 정보를 담은 [fetch 결과][DuckFetchResult]
     */
    suspend fun fetchFollow(
        @PK userId: String,
    ): DuckFetchResult<Follow>

    /**
     * 특정 [유저][User]가 참여중인 [채팅방][ChatRoom] 목록을 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param userId 조회할 [유저의 아이디][User.nickname]
     * @return 조회된 [채팅방][ChatRoom] 목록을 담은 [fetch 결과][DuckFetchResult]
     */
    suspend fun fetchChatRooms(
        @FK userId: String,
    ): DuckFetchResult<List<ChatRoom>>

    /**
     * 특정 [채팅방][ChatRoom]에 전송된 [채팅][Chat] 목록을 실시간으로 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param chatRoomId 조회할 [채팅방 아이디][ChatRoom.id]
     * @param onNewChat 새로운 채팅이 조회될 때마다 실행될 콜백.
     * 콜백의 인자로는 [채팅][Chat] 이 전달됩니다.
     * @param onError 채팅 조회 도중에 에러가 발생할 때마다 실행될 콜백.
     * 콜백의 인자로는 [에러][Throwable] 가 전달됩니다.
     *
     * @return Unit
     */
    suspend fun fetchRealtimeChats(
        @FK chatRoomId: String,
        onNewChat: (chat: Chat) -> Unit,
        onError: (exception: Throwable) -> Unit,
    )

    /**
     * 전체 [피드][Feed] 목록을 조회합니다.
     * 추천 시스템이 반영된 피드 목록 조회는 [fetchRecommendationFeeds] 를 사용하세요.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @return 전체 [피드][Feed] 목록을 담은 [fetch 결과][DuckFetchResult]
     */
    suspend fun fetchAllFeeds(): DuckFetchResult<List<Feed>>

    /**
     * 특정 유저의 취향이 반영된 [피드][Feed] 목록을 조회합니다.
     * 내부적으로 [duckie-recommender-sysytem](https://github.com/duckie-team/duckie-recommender-system) 을 이용합니다.
     * 추천 시스템이 적용되지 않은 전체 [피드][Feed] 목록 조회는 [fetchAllFeeds] 를 사용하세요.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param userId 조회할 [유저의 아이디][User.nickname]
     * @return 추천된 [피드][Feed] 목록을 담은 [fetch 결과][DuckFetchResult]
     */
    suspend fun fetchRecommendationFeeds(
        @PK userId: String,
    ): DuckFetchResult<List<Feed>>

    /**
     * 주어진 [유저][User]가 마지막으로 [읽은 채팅][ChatRead]의 목록을 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param userId 조회할 [유저의 아이디][User.nickname]
     * @return 조회된 마지막으로 [읽은 채팅][ChatRead]의 목록을 담은 [fetch 결과][DuckFetchResult]
     */
    @Unsupported
    suspend fun fetchChatRead(
        @PK @FK userId: String,
    ): DuckApiResult<ChatRead>

    /**
     * 주어진 피드가 받은 [좋아요][Heart] 목록을 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param target 조회할 [좋아요][Heart]의 대상
     * @param targetId 조회할 [피드 아이디][Feed.id]
     * @return 조회된 [좋아요][Heart] 목록을 담은 [fetch 결과][DuckFetchResult]
     */
    @Unsupported
    suspend fun fetchHeart(
        target: HeartTarget,
        @PK @FK targetId: String,
    ): DuckApiResult<Heart>

    /**
     * 주어진 피드에 작성된 [댓글][Comment] 목록을 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param feedId 조회할 [피드 아이디][Feed.id]
     * @return 조회된 [댓글][Comment] 목록을 담은 [fetch 결과][DuckFetchResult]
     */
    suspend fun fetchComments(
        @FK feedId: String,
    ): DuckApiResult<List<Comment>>

    /**
     * 주어진 덕딜피드로부터 등록된 [리뷰][DealReview] 정보를 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param feedId 조회할 [덕딜피드 아이디][Feed.id].
     * 주어진 피드 아이디가 덕딜피드 아이디인지는 검사하지 않습니다.
     * 해당 아이디에 등록된 리뷰가 없다면 피드 종류에 관계 없이 항상
     * [DuckFetchResult.Empty] 를 반환합니다.
     * @return 조회된 [리뷰][DealReview] 정보를 담은 [fetch 결과][DuckFetchResult]
     */
    // See: https://github.com/duckie-team/duckie-app-mvp/issues/32
    suspend fun fetchReview(
        @FK feedId: String,
    ): DuckApiResult<DealReview>

    /**
     * 주어진 덕딜피드로부터 [판매 요청][SaleRequest]된 기록을 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param feedId 조회할 [덕딜피드 아이디][Feed.id].
     * 주어진 피드 아이디가 덕딜피드 아이디인지는 검사하지 않습니다.
     * 해당 아이디에 등록된 리뷰가 없다면 피드 종류에 관계 없이 항상
     * [DuckFetchResult.Empty] 를 반환합니다.
     * @return 조회된 [판매 요청][SaleRequest] 목록을 담은 [fetch 결과][DuckFetchResult]
     */
    suspend fun fetchSaleRequest(
        @FK feedId: String,
    ): DuckApiResult<List<SaleRequest>>
}
