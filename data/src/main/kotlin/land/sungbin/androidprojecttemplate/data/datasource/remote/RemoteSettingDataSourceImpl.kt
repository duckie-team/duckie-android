package land.sungbin.androidprojecttemplate.data.datasource.remote

import land.sungbin.androidprojecttemplate.data.mapper.toData
import land.sungbin.androidprojecttemplate.data.mapper.toDomain
import land.sungbin.androidprojecttemplate.data.model.SettingData
import land.sungbin.androidprojecttemplate.domain.model.SettingEntity
import javax.inject.Inject

private var dummySetting = SettingData(
    activityNotification = false,
    messageNotification = true,
)

class RemoteSettingDataSourceImpl @Inject constructor() : RemoteSettingDataSource {

    override fun fetchSetting(): SettingEntity {
        return dummySetting.toDomain()
    }

    override fun updateSetting(
        entity: SettingEntity,
    ) {
        dummySetting = entity.toData()
    }
}
