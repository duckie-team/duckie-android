@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import land.sungbin.androidprojecttemplate.data.repository.util.setDefaultFailure
import land.sungbin.androidprojecttemplate.domain.repository.AssertRepository

class AssertRepositoryImpl : AssertRepository {
    override suspend fun checkNicknameAvailable(
        nickname: String,
    ) = suspendCancellableCoroutine<Boolean> { continuation ->
        Firebase.firestore.collection("users")
            .whereEqualTo("nickname", nickname)
            .get()
            .addOnSuccessListener { result ->
                continuation.resume(
                    value = result.isEmpty,
                )
            }
            .setDefaultFailure(
                continuation = continuation,
            )
    }
}
