/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.search.datasource

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.data.search.dao.SearchDao
import team.duckie.app.android.data.tag.model.SearchEntity
import team.duckie.app.android.util.kotlin.fastMap
import javax.inject.Inject

class SearchLocalDataSourceImpl @Inject constructor(
    private val searchDao: SearchDao,
): SearchLocalDataSource {

    /** [getSearchEntityByKeyword]를 통해 entity를 가져와 삭제한다. */
    override suspend fun clearRecentSearch(keyword: String) {
        getSearchEntityByKeyword(keyword = keyword)?.let { entity ->
            searchDao.delete(entity = entity)
        }
    }

    /** 최근 검색어를 모두 삭제한다. */
    override suspend fun clearAllRecentSearch() {
        searchDao.deleteAll()
    }

    /** 최근 검색어를 가져온다 */
    override suspend fun getRecentSearch(): ImmutableList<String> {
        return getAllSearchEntity().fastMap { it.keyword }.toImmutableList()
    }

    /** 최근 검색어를 저장한다. */
    override suspend fun saveRecentSearch(keyword: String) {
        searchDao.insert(SearchEntity(keyword = keyword))
    }

    /** [keyword] 를 통해 [SearchEntity] 를 가져온다. */
    private fun getSearchEntityByKeyword(keyword: String): SearchEntity? {
        return searchDao.getEntityByKeyword(keyword = keyword)
    }

    /** [SearchEntity] 를 모두 가져온다. */
    private suspend fun getAllSearchEntity(): List<SearchEntity> {
        return searchDao.getAll()
    }
}
