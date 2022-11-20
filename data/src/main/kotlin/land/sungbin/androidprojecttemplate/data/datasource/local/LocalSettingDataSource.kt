@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.datasource.local

import land.sungbin.androidprojecttemplate.data.model.AccountInformationData
import land.sungbin.androidprojecttemplate.data.model.SettingData
import land.sungbin.androidprojecttemplate.domain.model.AccountInformationEntity
import land.sungbin.androidprojecttemplate.domain.model.SettingEntity

interface LocalSettingDataSource {

    suspend fun fetchSetting(): SettingEntity

    suspend fun saveSetting(setting: SettingData)

    suspend fun fetchAccountInformation(): AccountInformationEntity

    suspend fun saveAccountInformation(accountInformation: AccountInformationData)
}
