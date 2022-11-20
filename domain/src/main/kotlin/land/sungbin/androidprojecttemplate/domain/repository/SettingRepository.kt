package land.sungbin.androidprojecttemplate.domain.repository

import land.sungbin.androidprojecttemplate.domain.model.AccountInformationEntity
import land.sungbin.androidprojecttemplate.domain.model.SettingEntity

interface SettingRepository : DuckRepository {

    suspend fun fetchSetting(): SettingEntity

    suspend fun updateSetting(entity: SettingEntity)

    suspend fun fetchAccountInformation(): AccountInformationEntity
}
