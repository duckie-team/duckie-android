@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.repository

import kotlinx.coroutines.suspendCancellableCoroutine
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
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.repository.FetchRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckFetchResult

class FetchRepositoryImpl : FetchRepository {
    override suspend fun fetchUser(
        id: String,
    ) = suspendCancellableCoroutine<DuckFetchResult<User>> { continuation ->
        TODO("Not yet implemented")
    }

    override suspend fun fetchFollow(
        userId: String,
    ) = suspendCancellableCoroutine<DuckFetchResult<Follow>> { continuation ->
        TODO("Not yet implemented")
    }

    override suspend fun fetchChatRooms(
        userId: String,
    ) = suspendCancellableCoroutine<DuckFetchResult<List<ChatRoom>>> { continuation ->
        TODO("Not yet implemented")
    }

    override suspend fun fetchChats(
        chatRoomId: String,
    ) = suspendCancellableCoroutine<DuckFetchResult<List<Chat>>> { continuation ->
        TODO("Not yet implemented")
    }

    override suspend fun fetchAllFeeds() =
        suspendCancellableCoroutine<DuckFetchResult<List<Feed>>> { continuation ->
            TODO("Not yet implemented")
        }

    override suspend fun fetchRecommendationFeeds(
        userId: String,
    ) = suspendCancellableCoroutine<DuckFetchResult<List<Feed>>> { continuation ->
        TODO("Not yet implemented")
    }

    @Unsupported
    override suspend fun fetchChatRead(
        userId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<ChatRead>> { continuation ->
        TODO("Not yet implemented")
    }

    @Unsupported
    override suspend fun fetchHeart(
        feedId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<Heart>> { continuation ->
        TODO("Not yet implemented")
    }

    override suspend fun fetchComments(
        feedId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<List<Comment>>> { continuation ->
        TODO("Not yet implemented")
    }

    override suspend fun fetchReview(
        feedId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<DealReview>> { continuation ->
        TODO("Not yet implemented")
    }

    override suspend fun fetchSaleRequest(
        feedId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<List<SaleRequest>>> { continuation ->
        TODO("Not yet implemented")
    }
}
