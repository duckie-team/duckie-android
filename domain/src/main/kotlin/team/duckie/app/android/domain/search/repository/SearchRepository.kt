/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.search.repository

import team.duckie.app.android.domain.search.model.Search

interface SearchRepository {
    suspend fun getSearch(query: String, page: Int, type: String): Search
}
