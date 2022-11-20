@file:Suppress("KDocFields")

package land.sungbin.androidprojecttemplate.data.datasource.remote

import land.sungbin.androidprojecttemplate.domain.model.AccountInformationEntity
import land.sungbin.androidprojecttemplate.domain.model.SettingEntity

interface RemoteSettingDataSource {

    fun fetchSetting(): SettingEntity

    fun updateSetting(entity: SettingEntity)

    fun fetchAccountInformation(): AccountInformationEntity
}
