/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.search

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.search.repository.SearchRepository
import team.duckie.app.android.domain.search.usecase.GetSearchUseCase
import team.duckie.app.android.domain.search.usecase.SearchExamsUseCase
import team.duckie.app.android.domain.search.usecase.SearchTagsUseCase
import team.duckie.app.android.domain.search.usecase.SearchUsersUseCase

@Module
@InstallIn(SingletonComponent::class)
object SearchUseCaseModule {

    @Provides
    fun provideGetSearchUseCase(repository: SearchRepository): GetSearchUseCase {
        return GetSearchUseCase(repository)
    }

    @Provides
    fun provideSearchUsersUseCase(repository: SearchRepository): SearchUsersUseCase {
        return SearchUsersUseCase(repository)
    }

    @Provides
    fun provideSearchTagsUseCase(repository: SearchRepository): SearchTagsUseCase {
        return SearchTagsUseCase(repository)
    }

    @Provides
    fun provideSearchExamsUseCase(repository: SearchRepository): SearchExamsUseCase {
        return SearchExamsUseCase(repository)
    }
}
