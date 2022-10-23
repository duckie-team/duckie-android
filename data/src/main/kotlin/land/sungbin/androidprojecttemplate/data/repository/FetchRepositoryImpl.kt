@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import land.sungbin.androidprojecttemplate.data.repository.constants.CollectionNames
import land.sungbin.androidprojecttemplate.data.repository.util.setDefaultFailure
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
    @Suppress("UNCHECKED_CAST")
    private fun <V, T : DuckApiResult<V>> findObject(
        collection: String,
        field: String? = null,
        value: String? = null,
        continuation: Continuation<T>,
    ) {
        Firebase.firestore
            .collection(collection)
            .run {
                if (field != null && value != null) {
                    whereEqualTo(field, value)
                } else {
                    this
                }
            }
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    continuation.resume(
                        value = DuckFetchResult.Empty<V>() as T,
                    )
                } else {
                    continuation.resume(
                        value = DuckFetchResult.Success(
                            value = result.documents.first().toObject(User::class.java)!!,
                        ) as T,
                    )
                }
            }
            .setDefaultFailure(
                continuation = continuation,
            )
    }

    @Suppress("UNCHECKED_CAST")
    private inline fun <reified V : Any, T : DuckApiResult<V>> findObjects(
        collection: String,
        field: String? = null,
        value: String? = null,
        continuation: Continuation<T>,
    ) {
        Firebase.firestore
            .collection(collection)
            .run {
                if (field != null && value != null) {
                    whereEqualTo(field, value)
                } else {
                    this
                }
            }
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    continuation.resume(
                        value = DuckFetchResult.Empty<V>() as T,
                    )
                } else {
                    continuation.resume(
                        value = DuckFetchResult.Success(
                            value = result.toObjects<V>(),
                        ) as T,
                    )
                }
            }
            .setDefaultFailure(
                continuation = continuation,
            )
    }

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

    override suspend fun fetchChats(
        chatRoomId: String,
    ) = suspendCancellableCoroutine<DuckFetchResult<List<Chat>>> { continuation ->
        findObjects(
            collection = CollectionNames.Chat,
            field = Chat::chatRoomId.name,
            value = chatRoomId,
            continuation = continuation,
        )
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
