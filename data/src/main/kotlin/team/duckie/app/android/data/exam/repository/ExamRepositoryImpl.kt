/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.repository

import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.data.exam.datasource.remote.ExamDataSource
import team.duckie.app.android.data.exam.mapper.toData
import team.duckie.app.android.domain.exam.model.ExamParam
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.util.kotlin.fastMap

class ExamRepositoryImpl @Inject constructor(
    private val examDataSource: ExamDataSource,
) : ExamRepository {
    override suspend fun makeExam(examParam: ExamParam): Boolean {
        return examDataSource.postExams(examParam.toData())
    }

    override suspend fun getCategories(withPopularTags: Boolean): ImmutableList<Category> {
        return examDataSource.getCategories(withPopularTags)
            .fastMap { it.toDomain() }
            .toImmutableList()
    }
}
