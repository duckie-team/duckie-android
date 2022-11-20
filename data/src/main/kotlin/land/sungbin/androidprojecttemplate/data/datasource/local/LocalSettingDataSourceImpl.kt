@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.datasource.local

import land.sungbin.androidprojecttemplate.data.datasource.local.room.SettingDao
import land.sungbin.androidprojecttemplate.data.mapper.toDomain
import land.sungbin.androidprojecttemplate.data.model.AccountInformationData
import land.sungbin.androidprojecttemplate.data.model.SettingData
import land.sungbin.androidprojecttemplate.domain.model.AccountInformationEntity
import land.sungbin.androidprojecttemplate.domain.model.SettingEntity
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

    override suspend fun fetchAccountInformation(): AccountInformationEntity {
        return settingDao.fetchAccountInformation().toDomain()
    }

    override suspend fun saveAccountInformation(accountInformation: AccountInformationData) {
        settingDao.insertAccountInformation(
            accountInformation = accountInformation,
        )
    }
}
