package team.duckie.app.android.data.datasource.remote

import team.duckie.app.data.mapper.toData
import team.duckie.app.data.mapper.toDomain
import team.duckie.app.data.model.AccountInformationData
import team.duckie.app.data.model.SettingData
import team.duckie.app.android.domain.constants.AccountType
import team.duckie.app.android.domain.model.AccountInformationEntity
import team.duckie.app.domain.model.SettingEntity
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
        accountType = team.duckie.app.android.domain.constants.AccountType.KAKAO,
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

    override fun fetchAccountInformation(): team.duckie.app.android.domain.model.AccountInformationEntity {
        return SettingFakeData.dummyAccountInformationData.toDomain()
    }
}
