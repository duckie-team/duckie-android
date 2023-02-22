/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.search.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User

interface SearchRepository {
    suspend fun searchUsers(query: String): Flow<PagingData<User>>

    suspend fun searchTags(query: String): Flow<PagingData<Tag>>

    suspend fun searchExams(query: String): Flow<PagingData<Exam>>
}
