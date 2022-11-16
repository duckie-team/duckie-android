@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.datasource.local

import land.sungbin.androidprojecttemplate.data.model.SettingData
import land.sungbin.androidprojecttemplate.domain.model.SettingEntity

interface LocalSettingDataSource {

    suspend fun fetchSetting(): SettingEntity

    suspend fun saveSetting(setting: SettingData)
}
