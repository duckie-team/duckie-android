@file:Suppress("KDocFields")

package team.duckie.app.android.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import team.duckie.app.data.repository.constants.CollectionNames
import team.duckie.app.data.repository.util.defaultEventHandles
import team.duckie.app.android.domain.model.ChatRoom
import team.duckie.app.domain.repository.UpdateRepository
import team.duckie.app.domain.repository.result.DuckApiResult

class UpdateRepositoryImpl : UpdateRepository {
    override suspend fun joinChatRoom(
        userId: String,
        roomId: String,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->
        Firebase.firestore
            .collection(CollectionNames.ChatRoom)
            .document(roomId)
            .update(team.duckie.app.android.domain.model.ChatRoom::joinUsers.name, FieldValue.arrayUnion(userId))
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
            .update(team.duckie.app.android.domain.model.ChatRoom::joinUsers.name, FieldValue.arrayRemove(userId))
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
