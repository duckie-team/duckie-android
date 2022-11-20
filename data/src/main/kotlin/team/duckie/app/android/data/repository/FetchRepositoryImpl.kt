@file:Suppress("KDocFields")

package team.duckie.app.android.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import team.duckie.app.data.repository.constants.CollectionNames
import team.duckie.app.data.repository.util.findObject
import team.duckie.app.data.repository.util.findObjects
import team.duckie.app.domain.model.Chat
import team.duckie.app.android.domain.model.ChatRead
import team.duckie.app.android.domain.model.ChatRoom
import team.duckie.app.android.domain.model.Comment
import team.duckie.app.domain.model.DealReview
import team.duckie.app.domain.model.Feed
import team.duckie.app.domain.model.Follow
import team.duckie.app.domain.model.Heart
import team.duckie.app.domain.model.SaleRequest
import team.duckie.app.domain.model.User
import team.duckie.app.domain.model.constraint.HeartTarget
import team.duckie.app.domain.model.util.Unsupported
import team.duckie.app.domain.repository.FetchRepository
import team.duckie.app.domain.repository.result.DuckApiResult
import team.duckie.app.domain.repository.result.DuckFetchResult

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
    ) = suspendCancellableCoroutine<DuckFetchResult<List<team.duckie.app.android.domain.model.ChatRoom>>> { continuation ->
        findObjects(
            collection = CollectionNames.ChatRoom,
            field = team.duckie.app.android.domain.model.ChatRoom::ownerId.name,
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
    ) = suspendCancellableCoroutine<DuckApiResult<team.duckie.app.android.domain.model.ChatRead>> { continuation ->
        findObject(
            collection = CollectionNames.ChatRead,
            field = team.duckie.app.android.domain.model.ChatRead::userId.name,
            value = userId,
            continuation = continuation,
        )
    }

    override suspend fun fetchHearts(
        target: HeartTarget,
        targetId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<List<Heart>>> { continuation ->
        findObjects(
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
    ) = suspendCancellableCoroutine<DuckApiResult<List<team.duckie.app.android.domain.model.Comment>>> { continuation ->
        findObjects(
            collection = CollectionNames.Comment,
            field = team.duckie.app.android.domain.model.Comment::feedId.name,
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
