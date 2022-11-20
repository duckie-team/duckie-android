@file:Suppress("KDocFields")

package team.duckie.app.android.data.datasource.local

import team.duckie.app.data.datasource.local.room.SettingDao
import team.duckie.app.data.mapper.toDomain
import team.duckie.app.data.model.AccountInformationData
import team.duckie.app.data.model.SettingData
import team.duckie.app.android.domain.model.AccountInformationEntity
import team.duckie.app.domain.model.SettingEntity
import javax.inject.Inject

class LocalSettingDataSourceImpl @Inject constructor(
    private val settingDao: SettingDao,
) : LocalSettingDataSource {

    override suspend fun fetchSetting(): SettingEntity {
        return settingDao.fetchSetting().toDomain()
    }

    override suspend fun saveSetting(setting: SettingData) {
        settingDao.insertSetting(
            setting = setting,
        )
    }

    override suspend fun fetchAccountInformation(): team.duckie.app.android.domain.model.AccountInformationEntity {
        return settingDao.fetchAccountInformation().toDomain()
    }

    override suspend fun saveAccountInformation(accountInformation: AccountInformationData) {
        settingDao.insertAccountInformation(
            accountInformation = accountInformation,
        )
    }
}
