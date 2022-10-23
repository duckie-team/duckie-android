package land.sungbin.androidprojecttemplate.data.repository.util

import com.google.android.gms.tasks.Task
import kotlin.coroutines.Continuation
import kotlin.coroutines.resumeWithException

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
