@file:Suppress("KDocFields")

package team.duckie.app.android.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import team.duckie.app.data.repository.constants.CollectionNames
import team.duckie.app.data.repository.util.defaultEventHandles
import team.duckie.app.android.domain.model.ContentStayTime
import team.duckie.app.domain.model.FeedScore
import team.duckie.app.domain.model.util.Unsupported
import team.duckie.app.domain.repository.ScoreRepository
import team.duckie.app.domain.repository.result.DuckApiResult

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
        contentStayTime: team.duckie.app.android.domain.model.ContentStayTime,
    ) = TODO()
}
