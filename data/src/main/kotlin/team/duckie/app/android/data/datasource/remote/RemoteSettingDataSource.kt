@file:Suppress("KDocFields")

package team.duckie.app.android.data.datasource.remote

import team.duckie.app.android.domain.model.AccountInformationEntity
import team.duckie.app.domain.model.SettingEntity

interface RemoteSettingDataSource {

    fun fetchSetting(): SettingEntity

    fun updateSetting(entity: SettingEntity)

    fun fetchAccountInformation(): team.duckie.app.android.domain.model.AccountInformationEntity
}
