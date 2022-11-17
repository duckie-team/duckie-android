@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.repository

import land.sungbin.androidprojecttemplate.data.datasource.local.LocalSettingDataSource
import land.sungbin.androidprojecttemplate.data.datasource.remote.RemoteSettingDataSource
import land.sungbin.androidprojecttemplate.data.mapper.toData
import land.sungbin.androidprojecttemplate.domain.model.AccountInformationEntity
import land.sungbin.androidprojecttemplate.domain.model.SettingEntity
import land.sungbin.androidprojecttemplate.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val isOfflineMode: Boolean,
    private val localSettingDataSource: LocalSettingDataSource,
    private val remoteSettingDataSource: RemoteSettingDataSource,
) : SettingRepository {

    override suspend fun fetchSetting(): SettingEntity {
        return if (isOfflineMode) {
            localSettingDataSource.fetchSetting()
        } else {
            remoteSettingDataSource.fetchSetting().also { setting ->
                localSettingDataSource.saveSetting(
                    setting = setting.toData(),
                )
            }
        }
    }

    override suspend fun updateSetting(entity: SettingEntity) {
        remoteSettingDataSource.updateSetting(
            entity = entity,
        )
    }

    override suspend fun fetchAccountInformation(): AccountInformationEntity {
        return if (isOfflineMode) {
            localSettingDataSource.fetchAccountInformation()
        } else {
            remoteSettingDataSource.fetchAccountInformation().also { accountInformation ->
                localSettingDataSource.saveAccountInformation(
                    accountInformation = accountInformation.toData(),
                )
            }
        }
    }
}
