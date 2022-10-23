@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import land.sungbin.androidprojecttemplate.data.repository.constants.CollectionNames
import land.sungbin.androidprojecttemplate.data.repository.util.defaultEventHandles
import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.repository.UpdateRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult

class UpdateRepositoryImpl : UpdateRepository {
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
