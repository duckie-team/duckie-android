@file:Suppress("KDocFields")

package team.duckie.app.android.domain.usecase.fetch

import team.duckie.app.domain.repository.SettingRepository

class FetchSettingUseCase(
    private val repository: SettingRepository
) {
    suspend operator fun invoke() = kotlin.runCatching {
        repository.fetchSetting()
    }
}
