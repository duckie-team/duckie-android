@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine
import land.sungbin.androidprojecttemplate.data.repository.constants.CollectionNames
import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.repository.UpdateRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult

class UpdateRepositoryImpl : UpdateRepository {
    /**
     * UpdateRepository 에서 호출하는 Firestore API 의 기본 이벤트 동작을 정의합니다.
     *
     * 기본적으로 성공했을 때에는 [DuckApiResult.Success] 를 resume 하고,
     * 실패했을 경우에는 [DuckApiResult.Exception] 을 resume 합니다.
     *
     * @param continuation 이벤트를 받으면 처리할 [continuation][Continuation]
     */
    @Suppress("UNCHECKED_CAST")
    private fun <V, T : DuckApiResult<V>, TResult> Task<TResult>.defaultEventHandles(
        continuation: Continuation<T>,
    ) = this
        .addOnSuccessListener {
            continuation.resume(
                value = DuckApiResult.Success<V>() as T,
            )
        }
        .addOnFailureListener { exception ->
            continuation.resumeWithException(
                exception = exception,
            )
        }

    override suspend fun joinChatRoom(
        userId: String,
        roomId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->
        Firebase.firestore
            .collection(CollectionNames.ChatRoom)
            .document(roomId)
            .update(ChatRoom::joinUsers.name, FieldValue.arrayUnion(userId))
            .defaultEventHandles(
                continuation = continuation,
            )
    }

    override suspend fun leaveChatRoom(
        userId: String,
        roomId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->
        Firebase.firestore
            .collection(CollectionNames.ChatRoom)
            .document(roomId)
            .update(ChatRoom::joinUsers.name, FieldValue.arrayRemove(userId))
            .defaultEventHandles(
                continuation = continuation,
            )
    }

    override suspend fun deleteComment(
        commentId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->
        Firebase.firestore
            .collection(CollectionNames.Comment)
            .document(commentId)
            .delete()
            .defaultEventHandles(
                continuation = continuation,
            )
    }
}
