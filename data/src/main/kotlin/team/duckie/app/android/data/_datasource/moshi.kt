/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data._datasource

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import team.duckie.app.android.data.search.model.SearchData
import team.duckie.app.android.domain.search.model.Search

internal val MoshiBuilder = Moshi.Builder()
    .add(searchDataFactory)
    .addLast(KotlinJsonAdapterFactory())
    .build()

internal val searchDataFactory
    get() = PolymorphicJsonAdapterFactory.of(SearchData::class.java, "type")
        .withSubtype(SearchData.ExamSearchData::class.java, Search.Exam)
        .withSubtype(SearchData.TagSearchData::class.java, Search.Tags)
        .withSubtype(SearchData.UserSearchData::class.java, Search.User)
