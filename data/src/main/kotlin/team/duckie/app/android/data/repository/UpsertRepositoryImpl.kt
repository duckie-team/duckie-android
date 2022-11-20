@file:Suppress("KDocFields")

package team.duckie.app.android.data.repository

import kotlinx.coroutines.suspendCancellableCoroutine
import team.duckie.app.domain.model.Chat
import team.duckie.app.android.domain.model.ChatRead
import team.duckie.app.android.domain.model.ChatRoom
import team.duckie.app.android.domain.model.Comment
import team.duckie.app.domain.model.DealReview
import team.duckie.app.domain.model.Feed
import team.duckie.app.domain.model.Follow
import team.duckie.app.domain.model.Heart
import team.duckie.app.domain.model.Report
import team.duckie.app.domain.model.SaleRequest
import team.duckie.app.domain.model.User
import team.duckie.app.domain.model.util.Unsupported
import team.duckie.app.domain.repository.UpsertRepository
import team.duckie.app.domain.repository.result.DuckApiResult

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
        chatRoom: team.duckie.app.android.domain.model.ChatRoom,
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
        chatRead: team.duckie.app.android.domain.model.ChatRead,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->

    }

    override suspend fun upsertHeart(
        heart: Heart,
        isDeletion: Boolean,
    ) = suspendCancellableCoroutine<DuckApiResult<Nothing>> { continuation ->

    }

    override suspend fun upsertComment(
        comment: team.duckie.app.android.domain.model.Comment,
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
