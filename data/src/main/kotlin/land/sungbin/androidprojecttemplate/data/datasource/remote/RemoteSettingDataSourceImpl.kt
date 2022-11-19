package land.sungbin.androidprojecttemplate.data.datasource.remote

import land.sungbin.androidprojecttemplate.data.mapper.toData
import land.sungbin.androidprojecttemplate.data.mapper.toDomain
import land.sungbin.androidprojecttemplate.data.model.AccountInformationData
import land.sungbin.androidprojecttemplate.data.model.SettingData
import land.sungbin.androidprojecttemplate.domain.constants.toAccountType
import land.sungbin.androidprojecttemplate.domain.model.AccountInformationEntity
import land.sungbin.androidprojecttemplate.domain.model.SettingEntity
import javax.inject.Inject

private var dummySetting = SettingData(
    activityNotification = false,
    messageNotification = true,
)

private var dummyAccountInformationData = AccountInformationData(
    accountType = "KAKAO",
    email = "sh007100@naver.com"
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

    override fun fetchAccountInformation(): AccountInformationEntity {
        return dummyAccountInformationData.toDomain()
    }
}
