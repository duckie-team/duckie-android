package land.sungbin.androidprojecttemplate.domain.usecase.fetch

import land.sungbin.androidprojecttemplate.domain.repository.SettingRepository

class FetchAccountInformationUseCase(
    private val repository: SettingRepository,
) {

    suspend operator fun invoke() = kotlin.runCatching {
        repository.fetchAccountInformation()
    }
}