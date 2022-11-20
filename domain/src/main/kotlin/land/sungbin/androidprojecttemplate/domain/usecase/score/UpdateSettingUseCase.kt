package land.sungbin.androidprojecttemplate.domain.usecase.score

import land.sungbin.androidprojecttemplate.domain.model.SettingEntity
import land.sungbin.androidprojecttemplate.domain.repository.SettingRepository

class UpdateSettingUseCase(
    private val repository: SettingRepository,
) {

    suspend operator fun invoke(
        entity: SettingEntity,
    ) = kotlin.runCatching {
        repository.updateSetting(
            entity = entity,
        )
    }
}
