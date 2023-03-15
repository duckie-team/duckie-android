/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.search.datasource

import kotlinx.collections.immutable.ImmutableList

interface SearchLocalDataSource {

    suspend fun clearRecentSearch(keyword: String)

    suspend fun clearAllRecentSearch()

    suspend fun getRecentSearch(): ImmutableList<String>

    suspend fun saveRecentSearch(keyword: String)
}
