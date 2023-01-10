/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalCoroutinesApi::class)

package team.duckie.app.android.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import team.duckie.app.android.data.dummy.ExamDummyResponse
import team.duckie.app.android.data.exam.repository.ExamRepositoryImpl
import team.duckie.app.android.data.util.ApiTest
import team.duckie.app.android.data.util.buildMockHttpClient
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.util.kotlin.OutOfDateApi

@OutOfDateApi
class ExamRepositoryTest : ApiTest(
    client = buildMockHttpClient(content = ExamDummyResponse.RawData),
) {
    private val repository: ExamRepository by lazy { ExamRepositoryImpl() }

    @Test
    fun response_to_domain_model() = runTest {
        val actual = repository.getExam(1)
        if (isMock) {
            val expected = ExamDummyResponse.DomainData

            expectThat(actual).isEqualTo(expected)
        } else {
            println(actual)
        }
    }
}
