package team.duckie.app.android.domain.usecase.user

import team.duckie.app.domain.repository.UserRepository

class FetchCategoriesUseCase(
    private val repository: UserRepository,
) {
    suspend operator fun invoke() = runCatching {
        repository.fetchCategories()
    }
}
