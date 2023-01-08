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
import org.junit.After
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isNotEmpty
import team.duckie.app.android.data.category.repository.CategoryRepositoryImpl
import team.duckie.app.android.data.dummy.CategoryDummyResponse
import team.duckie.app.android.data.util.buildMockHttpClient
import team.duckie.app.android.domain.category.repository.CategoryRepository

class CategoryRepositoryTest : ApiTest(
    isMock = true,
    client = buildMockHttpClient(content = CategoryDummyResponse.RawData)
) {
    private val repository: CategoryRepository by lazy { CategoryRepositoryImpl() }

    @Test
    fun response_to_domain_model() = runTest {
        val actual = repository.getCategories(true)

        if (isMock) {
            val expected = CategoryDummyResponse.DomainData

            expectThat(actual).containsExactly(expected)
        } else {
            println(actual.first())
            expectThat(actual).isNotEmpty()
        }
    }

    @After
    fun closeClient() {
        client.close()
    }
}
