@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.datasource.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import land.sungbin.androidprojecttemplate.data.model.SettingData

@Dao
interface SettingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: SettingData)

    @Query("SELECT * FROM SettingData")
    suspend fun fetchSetting(): SettingData
}
