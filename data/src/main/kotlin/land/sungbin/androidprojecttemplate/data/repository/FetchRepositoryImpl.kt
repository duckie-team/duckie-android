@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import land.sungbin.androidprojecttemplate.data.repository.constants.CollectionNames
import land.sungbin.androidprojecttemplate.data.repository.util.findObject
import land.sungbin.androidprojecttemplate.data.repository.util.findObjects
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
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.repository.FetchRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckFetchResult

class FetchRepositoryImpl : FetchRepository {
    override suspend fun fetchUser(
        id: String,
    ) = suspendCancellableCoroutine<DuckFetchResult<User>> { continuation ->
        findObject(
            collection = CollectionNames.User,
            field = User::nickname.name,
            value = id,
            continuation = continuation,
        )
    }

    override suspend fun fetchFollow(
        userId: String,
    ) = suspendCancellableCoroutine<DuckFetchResult<Follow>> { continuation ->
        findObject(
            collection = CollectionNames.Follow,
            field = Follow::userId.name,
            value = userId,
            continuation = continuation,
        )
    }

    override suspend fun fetchChatRooms(
        userId: String,
    ) = suspendCancellableCoroutine<DuckFetchResult<List<ChatRoom>>> { continuation ->
        findObjects(
            collection = CollectionNames.ChatRoom,
            field = ChatRoom::ownerId.name,
            value = userId,
            continuation = continuation,
        )
    }

    override suspend fun fetchRealtimeChats(
        chatRoomId: String,
        onNewChat: (chat: Chat) -> Unit,
        onError: (exception: Throwable) -> Unit,
    ): () -> Unit {
        val database = FirebaseDatabase.getInstance()
            .reference
            .child(CollectionNames.Chat)
            .child(chatRoomId)
        val postListener = object : ValueEventListener {
            override fun onDataChange(
                snapshot: DataSnapshot,
            ) {
                val chat = snapshot.getValue(Chat::class.java)
                if (chat != null) {
                    onNewChat(
                        /* chat = */
                        chat,
                    )
                }
            }

            override fun onCancelled(
                error: DatabaseError,
            ) {
                onError(
                    /* exception = */
                    error.toException(),
                )
            }
        }
        database.addValueEventListener(postListener)
        return {
            database.removeEventListener(postListener)
        }
    }

    override suspend fun fetchAllFeeds() =
        suspendCancellableCoroutine<DuckFetchResult<List<Feed>>> { continuation ->
            findObjects(
                collection = CollectionNames.Feed,
                continuation = continuation,
            )
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
        findObject(
            collection = CollectionNames.ChatRead,
            field = ChatRead::userId.name,
            value = userId,
            continuation = continuation,
        )
    }

    @Unsupported
    override suspend fun fetchHeart(
        target: HeartTarget,
        targetId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<Heart>> { continuation ->
        findObject(
            collection = when (target) {
                HeartTarget.Feed -> CollectionNames.FeedHeart
                HeartTarget.Comment -> CollectionNames.CommentHeart
            },
            field = Heart::targetId.name,
            value = targetId,
            continuation = continuation,
        )
    }

    override suspend fun fetchComments(
        feedId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<List<Comment>>> { continuation ->
        findObjects(
            collection = CollectionNames.Comment,
            field = Comment::feedId.name,
            value = feedId,
            continuation = continuation,
        )
    }

    override suspend fun fetchReview(
        feedId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<DealReview>> { continuation ->
        findObject(
            collection = CollectionNames.DealReview,
            field = DealReview::feedId.name,
            value = feedId,
            continuation = continuation,
        )
    }

    override suspend fun fetchSaleRequest(
        feedId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<List<SaleRequest>>> { continuation ->
        findObjects(
            collection = CollectionNames.SaleRequest,
            field = SaleRequest::feedId.name,
            value = feedId,
            continuation = continuation,
        )
    }
}
