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
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import team.duckie.app.android.data.category.repository.CategoryRepositoryImpl
import team.duckie.app.android.data.dummy.CategoryDummyResponse
import team.duckie.app.android.data.rule.HttpClientTestRule
import team.duckie.app.android.data.util.buildMockHttpClient
import team.duckie.app.android.domain.category.repository.CategoryRepository

class CategoryRepositoryTest {
    private val client = buildMockHttpClient(content = CategoryDummyResponse.RawData)
    private val repository: CategoryRepository by lazy { CategoryRepositoryImpl() }

    @get:Rule
    val httpClientTestRule = HttpClientTestRule(client)

    @Test
    fun response_to_domain_model() = runTest {
        val actual = repository.getCategories(true)

        val expected = CategoryDummyResponse.DomainData

        expectThat(actual).containsExactly(expected)
    }

    @After
    fun closeClient() {
        client.close()
    }
}
