package land.sungbin.androidprojecttemplate.data.datasource.remote

import land.sungbin.androidprojecttemplate.data.mapper.toData
import land.sungbin.androidprojecttemplate.data.mapper.toDomain
import land.sungbin.androidprojecttemplate.data.model.AccountInformationData
import land.sungbin.androidprojecttemplate.data.model.SettingData
import land.sungbin.androidprojecttemplate.domain.constants.AccountType
import land.sungbin.androidprojecttemplate.domain.model.AccountInformationEntity
import land.sungbin.androidprojecttemplate.domain.model.SettingEntity
import javax.inject.Inject

/**
 * Setting에 사용되는 Fake Data 입니다.
 * [TODO] 추후에 실제 데이터를 받는 형식으로 교체되어야 합니다.
 */
object SettingFakeData {
    var dummySetting = SettingData(
        activityNotification = false,
        messageNotification = true,
    )

    var dummyAccountInformationData = AccountInformationData(
        accountType = AccountType.KAKAO,
        email = "sh007100@naver.com"
    )
}

class RemoteSettingDataSourceImpl @Inject constructor() : RemoteSettingDataSource {

    override fun fetchSetting(): SettingEntity {
        return SettingFakeData.dummySetting.toDomain()
    }

    override fun updateSetting(
        entity: SettingEntity,
    ) {
        SettingFakeData.dummySetting = entity.toData()
    }

    override fun fetchAccountInformation(): AccountInformationEntity {
        return SettingFakeData.dummyAccountInformationData.toDomain()
    }
}
