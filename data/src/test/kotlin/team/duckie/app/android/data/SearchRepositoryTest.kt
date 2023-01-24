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
import team.duckie.app.android.data.dummy.SearchDummyResponse
import team.duckie.app.android.data.search.repository.SearchRepositoryImpl
import team.duckie.app.android.data.util.ApiTest
import team.duckie.app.android.data.util.buildMockHttpClient
import team.duckie.app.android.domain.search.repository.SearchRepository
import team.duckie.app.android.util.kotlin.OutOfDateApi

@OutOfDateApi
class SearchRepositoryTest : ApiTest(
    client = buildMockHttpClient(content = SearchDummyResponse.RawData),
    isMock = false,
) {
    private val repository: SearchRepository by lazy { SearchRepositoryImpl() }

    @Test
    fun response_to_domain_model() = runTest {
        val actual = repository.getSearch("도", 1, "TAGS")
        if (isMock) {
            val expected = SearchDummyResponse.DomainData

            expectThat(actual).isEqualTo(expected)
        } else {
            println(actual)
        }
    }
}
