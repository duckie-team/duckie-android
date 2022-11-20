package team.duckie.app.android.data.repository.util

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import team.duckie.app.domain.repository.result.DuckApiResult
import team.duckie.app.domain.repository.result.DuckFetchResult

/**
 * Firestore 이벤트의 기본 실패 동작을 정의합니다.
 *
 * @param continuation 실패시 exception 과 함께 resume 할 continuation
 * @return Firestore 이벤트의 기본 실패 동작
 */
internal fun <T, TResult> Task<TResult>.setDefaultFailure(
    continuation: Continuation<T>,
) = addOnFailureListener { exception ->
    continuation.resumeWithException(
        exception = exception,
    )
}

/**
 * Firestore 에서 주어진 조건에 해당하는 데이터를 조회합니다.
 *
 * @param collection Firestore 의 Collection 이름
 * @param field PK 의 이름.
 * 만약 존재하지 않는다면 null 을 제공받습니다.
 * @param value PK 의 값.
 * 만약 존재하지 않는다면 null 을 제공받습니다.
 * @param isList 데이터가 [리스트][Collection] 형식으로 저장돼 있는지 여부
 * @param continuation 데이터를 조회한 후에 데이터를 [resume][Continuation.resume] 할 [continuation][Continuation]
 */
@Suppress("UNCHECKED_CAST")
private inline fun <reified V : Any, T : DuckApiResult<V>> findObjectImpl(
    collection: String,
    field: String? = null,
    value: String? = null,
    isList: Boolean,
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
        .addOnSuccessListener { snapshot ->
            if (snapshot.isEmpty) {
                continuation.resume(
                    value = DuckFetchResult.Empty<V>() as T,
                )
            } else {
                continuation.resume(
                    value = DuckFetchResult.Success(
                        value = when (isList) {
                            true -> snapshot.toObjects<V>()
                            else -> snapshot.documents.first().toObject(V::class.java)!!
                        }
                    ) as T,
                )
            }
        }
        .setDefaultFailure(
            continuation = continuation,
        )
}

/**
 * Firestore 에서 주어진 조건에 해당하는 데이터를 조회합니다.
 *
 * @param collection Firestore 의 Collection 이름
 * @param field PK 의 이름.
 * 만약 존재하지 않는다면 null 을 제공받습니다.
 * @param value PK 의 값.
 * 만약 존재하지 않는다면 null 을 제공받습니다.
 * @param continuation 데이터를 조회한 후에 데이터를 [resume][Continuation.resume] 할 [continuation][Continuation]
 */
internal inline fun <reified V : Any, T : DuckApiResult<V>> findObject(
    collection: String,
    field: String? = null,
    value: String? = null,
    continuation: Continuation<T>,
) = findObjectImpl(
    collection = collection,
    field = field,
    value = value,
    isList = false,
    continuation = continuation,
)

/**
 * Firestore 에서 주어진 조건에 해당하는 리스트 형식의 데이터를 조회합니다.
 *
 * @param collection Firestore 의 Collection 이름
 * @param field PK 의 이름.
 * 만약 존재하지 않는다면 null 을 제공받습니다.
 * @param value PK 의 값.
 * 만약 존재하지 않는다면 null 을 제공받습니다.
 * @param continuation 데이터를 조회한 후에 데이터를 [resume][Continuation.resume] 할 [continuation][Continuation]
 */
internal inline fun <reified V : Any, T : DuckApiResult<V>> findObjects(
    collection: String,
    field: String? = null,
    value: String? = null,
    continuation: Continuation<T>,
) = findObjectImpl(
    collection = collection,
    field = field,
    value = value,
    isList = true,
    continuation = continuation,
)

/**
 * UpdateRepository 에서 호출하는 Firestore API 의 기본 이벤트 동작을 정의합니다.
 *
 * 기본적으로 성공했을 때에는 [DuckApiResult.Success] 를 resume 하고,
 * 실패했을 경우에는 [DuckApiResult.Exception] 을 resume 합니다.
 *
 * @param continuation 이벤트를 받으면 처리할 [continuation][Continuation]
 * @return 이벤트 처리 방식이 적용된 [Task]
 */
@Suppress("UNCHECKED_CAST")
internal fun <V, T : DuckApiResult<V>, TResult> Task<TResult>.defaultEventHandles(
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
