package land.sungbin.androidprojecttemplate.data.datasource.remote

import io.ktor.client.HttpClient
import land.sungbin.androidprojecttemplate.domain.model.NotificationItem
import java.util.Date
import javax.inject.Inject

class NotificationDataSource @Inject constructor(
    private val client: HttpClient,
) {
    suspend fun getNotifications(
        userId: String,
    ): List<NotificationItem> {
//        // TODO(riflockle7) API 요청 로직
//        val request = client.get {
//            url {
//                host = "$BaseUrl/router"
//                protocol = URLProtocol.WS
//                port = protocol.defaultPort
//            }
//        }
//        val body: List<NotificationData> = request.body()
//
//        return listOf(
//            NotificationData(),
//            NotificationData(),
//            NotificationData(),
//            NotificationData(),
//            NotificationData(),
//            NotificationData(),
//            NotificationData(),
//            NotificationData(),
//        ).map { notificationData -> notificationData.toDomain() }

        return listOf(
            NotificationItem.NewComment(
                id = 0,
                profileUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                createdAt = Date(),
                description = "글 뭐라써야하지 모르겠는걸 아 갑자기 초코우유 먹고 싶다.",
                targetUserId = "targetUserId",
                feedId = "feedId",
            ),
            NotificationItem.NewHeart(
                id = 1,
                profileUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                createdAt = Date(),
                description = "글 뭐라써야하지 모르겠는걸 아 갑자기 초코우유 먹고 싶다.",
                targetUserId = "targetUserId",
                feedId = "feedId",
            ),
            NotificationItem.NewFollower(
                id = 2,
                profileUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                createdAt = Date(),
                targetUserId = "덕통사고",
                isFollowed = true,
            ),
            NotificationItem.RequireWriteReview(
                id = 3,
                profileUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                createdAt = Date(),
                duckDealTitle = "곰돌이 푸 파우치",
                duckDealUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            ),
            NotificationItem.RequireChangeDealState(
                id = 4,
                profileUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                createdAt = Date(),
                duckDealTitle = "어쩌구 제품...",
                duckDealUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            ),
            NotificationItem.RequireUpToDuckDeal(
                id = 5,
                profileUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                createdAt = Date(),
                targetUserId = "우주사령관",
                duckDealUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            ),
            NotificationItem.NewComment(
                id = 6,
                profileUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                createdAt = Date(),
                description = "글 뭐라써야하지 모르겠는걸 아 갑자기 초코우유 먹고 싶다.",
                targetUserId = "targetUserId",
                feedId = "feedId",
            ),
            NotificationItem.NewHeart(
                id = 7,
                profileUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                createdAt = Date(),
                description = "글 뭐라써야하지 모르겠는걸 아 갑자기 초코우유 먹고 싶다.",
                targetUserId = "targetUserId",
                feedId = "feedId",
            ),
            NotificationItem.NewFollower(
                id = 8,
                profileUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                createdAt = Date(),
                targetUserId = "덕통사고",
                isFollowed = true,
            ),
            NotificationItem.RequireWriteReview(
                id = 9,
                profileUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                createdAt = Date(),
                duckDealTitle = "곰돌이 푸 파우치",
                duckDealUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            ),
            NotificationItem.RequireChangeDealState(
                id = 10,
                profileUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                createdAt = Date(),
                duckDealTitle = "어쩌구 제품...",
                duckDealUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            ),
            NotificationItem.RequireUpToDuckDeal(
                id = 11,
                profileUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                createdAt = Date(),
                targetUserId = "우주사령관",
                duckDealUrl = "https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            ),
        );
    }
}