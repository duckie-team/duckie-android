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
import land.sungbin.androidprojecttemplate.domain.repository.UpsertRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult

class UpsertRepositoryImpl : UpsertRepository {
    override suspend fun upsertUser(
        user: User,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertFollow(
        follow: Follow,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertChatRoom(
        chatRoom: ChatRoom,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertChat(
        chat: Chat,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertFeed(
        feed: Feed,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    @Unsupported
    override suspend fun upsertChatRead(
        chatRead: ChatRead,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertHeart(
        heart: Heart,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertComment(
        comment: Comment,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertReview(
        review: DealReview,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertSaleRequest(
        saleRequest: SaleRequest,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertReport(
        report: Report,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    @Unsupported
    override suspend fun upsertFeedScore(
        feedScore: FeedScore,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    @Unsupported
    override suspend fun upsertContentStayTime(
        contentStayTime: ContentStayTime,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }
}
