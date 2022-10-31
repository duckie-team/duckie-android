@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.repository

import kotlinx.coroutines.suspendCancellableCoroutine
import land.sungbin.androidprojecttemplate.domain.model.Chat
import land.sungbin.androidprojecttemplate.domain.model.ChatRead
import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.model.Comment
import land.sungbin.androidprojecttemplate.domain.model.DealReview
import land.sungbin.androidprojecttemplate.domain.model.Feed
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
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->

    }

    override suspend fun upsertFollow(
        follow: Follow,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->

    }

    override suspend fun upsertChatRoom(
        chatRoom: ChatRoom,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->

    }

    override suspend fun upsertChat(
        chat: Chat,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->

    }

    override suspend fun upsertFeed(
        feed: Feed,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->

    }

    @Unsupported
    override suspend fun upsertChatRead(
        chatRead: ChatRead,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->

    }

    override suspend fun upsertHeart(
        heart: Heart,
        isDeletion: Boolean,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->

    }

    override suspend fun upsertComment(
        comment: Comment,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->

    }

    override suspend fun upsertReview(
        review: DealReview,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->

    }

    override suspend fun upsertSaleRequest(
        saleRequest: SaleRequest,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->

    }

    override suspend fun upsertReport(
        report: Report,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->

    }
}
