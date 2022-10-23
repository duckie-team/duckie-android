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
    ) = suspendCancellableCoroutine<DuckFetchResult<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchFollow(
        userId: String,
    ) = suspendCancellableCoroutine<DuckFetchResult<Follow>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchChatRooms(
        userId: String,
    ) = suspendCancellableCoroutine<DuckFetchResult<List<ChatRoom>>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchChats(
        chatRoomId: String,
    ) = suspendCancellableCoroutine<DuckFetchResult<List<Chat>>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAllFeeds() =
        suspendCancellableCoroutine<DuckFetchResult<List<Feed>>> {
            TODO("Not yet implemented")
        }

    override suspend fun fetchRecommendationFeeds(
        userId: String,
    ) = suspendCancellableCoroutine<DuckFetchResult<List<Feed>>> {
        TODO("Not yet implemented")
    }

    @Unsupported
    override suspend fun fetchChatRead(
        userId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<ChatRead>> {
        TODO("Not yet implemented")
    }

    @Unsupported
    override suspend fun fetchHeart(
        feedId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<Heart>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchComments(
        feedId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<List<Comment>>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchReview(
        feedId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<DealReview>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSaleRequest(
        feedId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<List<SaleRequest>>> {
        TODO("Not yet implemented")
    }
}
