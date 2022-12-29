/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.category

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.category.repository.CategoryRepository
import team.duckie.app.android.domain.category.usecase.GetCategoriesUseCase

@Module
@InstallIn(SingletonComponent::class)
object CategoryUseCaseModule {
    @Provides
    fun provideCategoriesUseCase(repository: CategoryRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository)
    }
}
