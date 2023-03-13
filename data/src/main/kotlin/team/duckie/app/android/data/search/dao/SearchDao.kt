/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.search.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import team.duckie.app.android.data._local.BaseDao
import team.duckie.app.android.data.tag.model.SearchEntity

@Dao
interface SearchDao : BaseDao<SearchEntity> {

    @Query("SELECT * FROM tag_entity")
    suspend fun getAll(): List<SearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: SearchEntity): Long
}
