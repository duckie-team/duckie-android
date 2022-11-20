@file:Suppress("KDocFields")

package team.duckie.app.android.data.datasource.local

import team.duckie.app.data.model.AccountInformationData
import team.duckie.app.data.model.SettingData
import team.duckie.app.android.domain.model.AccountInformationEntity
import team.duckie.app.domain.model.SettingEntity

interface LocalSettingDataSource {

    suspend fun fetchSetting(): SettingEntity

    suspend fun saveSetting(setting: SettingData)

    suspend fun fetchAccountInformation(): team.duckie.app.android.domain.model.AccountInformationEntity

    suspend fun saveAccountInformation(accountInformation: AccountInformationData)
}
