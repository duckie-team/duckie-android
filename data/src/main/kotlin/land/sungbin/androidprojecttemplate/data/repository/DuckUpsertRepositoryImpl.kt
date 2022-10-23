@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.repository

import land.sungbin.androidprojecttemplate.domain.model.Chat
import land.sungbin.androidprojecttemplate.domain.model.ChatRead
import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.model.Comment
import land.sungbin.androidprojecttemplate.domain.model.ContentStayTime
import land.sungbin.androidprojecttemplate.domain.model.DealReview
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.FeedScore
import land.sungbin.androidprojecttemplate.domain.model.Follow
import land.sungbin.androidprojecttemplate.domain.model.Heart
import land.sungbin.androidprojecttemplate.domain.model.Report
import land.sungbin.androidprojecttemplate.domain.model.SaleRequest
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.repository.DuckUpsertRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult

private typealias DuckUpsertResult = DuckApiResult<Nothing>

class DuckUpsertRepositoryImpl : DuckUpsertRepository {
    override suspend fun upsertUser(
        user: User,
    ): DuckUpsertResult {
        TODO("Not yet implemented")
    }

    override suspend fun upsertFollow(
        follow: Follow,
    ): DuckUpsertResult {
        TODO("Not yet implemented")
    }

    override suspend fun upsertChatRoom(
        chatRoom: ChatRoom,
    ): DuckUpsertResult {
        TODO("Not yet implemented")
    }

    override suspend fun upsertChat(
        chat: Chat,
    ): DuckUpsertResult {
        TODO("Not yet implemented")
    }

    override suspend fun upsertFeed(
        feed: Feed,
    ): DuckUpsertResult {
        TODO("Not yet implemented")
    }

    @Unsupported
    override suspend fun upsertChatRead(
        chatRead: ChatRead,
    ): DuckUpsertResult {
        TODO("Not yet implemented")
    }

    override suspend fun upsertHeart(
        heart: Heart,
    ): DuckUpsertResult {
        TODO("Not yet implemented")
    }

    override suspend fun upsertComment(
        comment: Comment,
    ): DuckUpsertResult {
        TODO("Not yet implemented")
    }

    override suspend fun upsertReview(
        review: DealReview,
    ): DuckUpsertResult {
        TODO("Not yet implemented")
    }

    override suspend fun upsertSaleRequest(
        saleRequest: SaleRequest,
    ): DuckUpsertResult {
        TODO("Not yet implemented")
    }

    override suspend fun upsertReport(
        report: Report,
    ): DuckUpsertResult {
        TODO("Not yet implemented")
    }

    @Unsupported
    override suspend fun upsertFeedScore(
        feedScore: FeedScore,
    ): DuckUpsertResult {
        TODO("Not yet implemented")
    }

    @Unsupported
    override suspend fun upsertContentStayTime(
        contentStayTime: ContentStayTime,
    ): DuckUpsertResult {
        TODO("Not yet implemented")
    }
}
