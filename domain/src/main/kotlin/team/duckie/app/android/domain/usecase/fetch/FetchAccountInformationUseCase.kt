package team.duckie.app.android.domain.usecase.fetch

import team.duckie.app.domain.repository.SettingRepository

class FetchAccountInformationUseCase(
    private val repository: SettingRepository,
) {

    suspend operator fun invoke() = kotlin.runCatching {
        repository.fetchAccountInformation()
    }
}
