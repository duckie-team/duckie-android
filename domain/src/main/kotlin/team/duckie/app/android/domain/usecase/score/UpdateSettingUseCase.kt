package team.duckie.app.android.domain.usecase.score

import team.duckie.app.domain.model.SettingEntity
import team.duckie.app.domain.repository.SettingRepository

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
