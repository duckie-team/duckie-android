@file:Suppress("KDocFields")

package team.duckie.app.android.data.datasource.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import team.duckie.app.data.model.AccountInformationData
import team.duckie.app.data.model.SettingData

@Dao
interface SettingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: SettingData)

    @Query("SELECT * FROM SettingData")
    suspend fun fetchSetting(): SettingData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccountInformation(accountInformation: AccountInformationData)

    @Query("SELECT * FROM AccountInformationData")
    suspend fun fetchAccountInformation(): AccountInformationData
}
