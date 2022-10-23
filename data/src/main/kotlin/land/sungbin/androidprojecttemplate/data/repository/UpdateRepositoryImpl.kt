@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.repository

import land.sungbin.androidprojecttemplate.domain.repository.UpdateRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult

class UpdateRepositoryImpl : UpdateRepository {
    override suspend fun joinChatRoom(
        userId: String,
        roomId: String,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun leaveChatRoom(
        userId: String,
        roomId: String,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteComment(
        commentId: String,
    ): DuckApiResult<Nothing> {
        TODO("Not yet implemented")
    }
}
