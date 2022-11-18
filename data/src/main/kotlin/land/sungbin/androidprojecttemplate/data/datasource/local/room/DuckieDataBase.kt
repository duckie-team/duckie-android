@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import land.sungbin.androidprojecttemplate.data.model.AccountInformationData
import land.sungbin.androidprojecttemplate.data.model.SettingData

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
