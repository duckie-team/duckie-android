/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalCoroutinesApi::class)

package team.duckie.app.android.data

import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import team.duckie.app.android.data.dummy.ExamDummyResponse
import team.duckie.app.android.data.exam.datasource.ExamInfoDataSource
import team.duckie.app.android.data.exam.model.ExamInfoEntity
import team.duckie.app.android.data.exam.repository.ExamRepositoryImpl
import team.duckie.app.android.data.util.ApiTest
import team.duckie.app.android.data.util.buildMockHttpClient
import team.duckie.app.android.domain.exam.repository.ExamRepository
import timber.log.Timber

class ExamRepositoryTest : ApiTest(
    client = buildMockHttpClient(content = ExamDummyResponse.RawData),
) {
    private val repository: ExamRepository by lazy {
        ExamRepositoryImpl(fuel = Fuel, examInfoDataSource = FakeExamInfoDataSourceImpl())
    }

    @Test
    fun response_to_domain_model() = runTest {
        val actual = repository.getExam(1)
        if (isMock) {
            val expected = ExamDummyResponse.DomainData

            expectThat(actual).isEqualTo(expected)
        } else {
            Timber.e(actual.toString())
        }
    }
}

// TODO(riflockle7): 실제 동작하는 코드에선 백방 에러 발생할 거 같은데...
class FakeExamInfoDataSourceImpl : ExamInfoDataSource {
    override suspend fun getFavoriteExams(): List<ExamInfoEntity> = listOf()

    override suspend fun getMadeExams(): List<ExamInfoEntity> = listOf()

    override suspend fun getSolvedExams(): List<ExamInfoEntity> = listOf()

    override suspend fun getRecentExams(): List<ExamInfoEntity> = listOf()
}
