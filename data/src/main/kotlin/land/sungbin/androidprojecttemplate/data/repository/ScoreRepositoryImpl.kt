@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import land.sungbin.androidprojecttemplate.data.repository.constants.CollectionNames
import land.sungbin.androidprojecttemplate.data.repository.util.defaultEventHandles
import land.sungbin.androidprojecttemplate.domain.model.ContentStayTime
import land.sungbin.androidprojecttemplate.domain.model.FeedScore
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.repository.ScoreRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult

class ScoreRepositoryImpl : ScoreRepository {
    override suspend fun updateFeedScore(
        feedScore: FeedScore,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->
        Firebase.firestore
            .collection(CollectionNames.FeedScore)
            .document(feedScore.feedId)
            .collection(feedScore.userId)
            .document(CollectionNames.FeedScore)
            .run {
                val stayTime = feedScore.stayTime
                val score = feedScore.score

                var task: Task<Void>? = null
                if (stayTime != null) {
                    task = update(FeedScore::stayTime.name, FieldValue.increment(stayTime.toLong()))
                }
                if (score != null) {
                    task = update(FeedScore::score.name, FieldValue.increment(score.toLong()))
                }
                task?.defaultEventHandles(
                    continuation = continuation,
                )
            }
    }

    @Unsupported
    override suspend fun updateContentStayTime(
        contentStayTime: ContentStayTime,
    ) = TODO()
}
