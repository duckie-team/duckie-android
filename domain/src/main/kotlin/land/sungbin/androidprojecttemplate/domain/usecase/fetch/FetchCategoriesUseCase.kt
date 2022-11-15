package land.sungbin.androidprojecttemplate.domain.usecase.fetch

import land.sungbin.androidprojecttemplate.domain.repository.UserRepository

class FetchCategoriesUseCase(
    private val repository: UserRepository,
) {
    suspend operator fun invoke() = runCatching {
        repository.fetchCategories()
    }
}