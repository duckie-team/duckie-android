/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.repository

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.model.ExamBody
import team.duckie.app.android.domain.exam.model.ExamFunding
import team.duckie.app.android.domain.exam.model.ExamInfo
import team.duckie.app.android.domain.exam.model.ExamThumbnailBody
import team.duckie.app.android.domain.exam.model.IgnoreExam
import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.home.model.HomeFunding
import team.duckie.app.android.domain.tag.model.Tag

@Immutable
interface ExamRepository {
    suspend fun makeExam(exam: ExamBody): Boolean

    suspend fun getExam(id: Int): Exam

    suspend fun getExamThumbnail(examThumbnailBody: ExamThumbnailBody): String

    suspend fun getExamMeFollowing(): Flow<PagingData<Exam>>

    suspend fun getMadeExams(): List<ExamInfo>

    suspend fun getSolvedExams(): List<ExamInfo>

    suspend fun getFavoriteExams(): List<ExamInfo>

    suspend fun getRecentExam(): List<ExamInfo>

    suspend fun getHeartExam(userId: Int): Flow<PagingData<ProfileExam>>

    suspend fun getSubmittedExam(userId: Int): Flow<PagingData<ProfileExam>>

    suspend fun getIgnoreExams(): List<IgnoreExam>

    suspend fun cancelExamIgnore(examId: Int): Boolean

    suspend fun getFunding(tagId: Int?, page: Int, limit: Int?): List<ExamFunding>

    suspend fun getUpcoming(page: Int): List<HomeFunding>

    suspend fun getTags(status: String): List<Tag>
}
