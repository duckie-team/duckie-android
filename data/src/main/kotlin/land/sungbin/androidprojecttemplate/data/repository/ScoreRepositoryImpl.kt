@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.repository

import kotlinx.coroutines.suspendCancellableCoroutine
import land.sungbin.androidprojecttemplate.domain.model.ContentStayTime
import land.sungbin.androidprojecttemplate.domain.model.FeedScore
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.repository.ScoreRepository

class ScoreRepositoryImpl : ScoreRepository {
    override suspend fun updateFeedScore(
        feedScore: FeedScore,
    ) = suspendCancellableCoroutine<Boolean> { continuation ->

    }

    @Unsupported
    override suspend fun updateContentStayTime(
        contentStayTime: ContentStayTime,
    ) = suspendCancellableCoroutine<Boolean> { continuation ->

    }
}
