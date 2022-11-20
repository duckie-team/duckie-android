@file:Suppress("KDocFields")

package team.duckie.app.android.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import team.duckie.app.data.model.AccountInformationData
import team.duckie.app.data.model.SettingData

@Database(
    entities = [
        SettingData::class,
        AccountInformationData::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class DuckieDataBase : RoomDatabase() {
    abstract fun settingDao(): SettingDao
}
