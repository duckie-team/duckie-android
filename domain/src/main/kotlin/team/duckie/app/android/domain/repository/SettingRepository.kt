package team.duckie.app.android.domain.repository

import team.duckie.app.android.domain.model.AccountInformationEntity
import team.duckie.app.domain.model.SettingEntity

interface SettingRepository : DuckRepository {

    suspend fun fetchSetting(): SettingEntity

    suspend fun updateSetting(entity: SettingEntity)

    suspend fun fetchAccountInformation(): team.duckie.app.android.domain.model.AccountInformationEntity
}
