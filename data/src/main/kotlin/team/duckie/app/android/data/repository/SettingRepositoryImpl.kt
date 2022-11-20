@file:Suppress("KDocFields")

package team.duckie.app.android.data.repository

import team.duckie.app.data.datasource.local.LocalSettingDataSource
import team.duckie.app.data.datasource.remote.RemoteSettingDataSource
import team.duckie.app.data.mapper.toData
import team.duckie.app.android.domain.model.AccountInformationEntity
import team.duckie.app.domain.model.SettingEntity
import team.duckie.app.domain.repository.SettingRepository
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

    override suspend fun fetchAccountInformation(): team.duckie.app.android.domain.model.AccountInformationEntity {
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
