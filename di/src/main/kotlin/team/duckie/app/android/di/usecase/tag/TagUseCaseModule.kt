/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.tag

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.tag.repository.TagRepository
import team.duckie.app.android.domain.tag.usecase.TagCreateUseCase

@Module
@InstallIn(SingletonComponent::class)
object TagUseCaseModule {
    @Provides
    fun provideTagCreateUseCase(repository: TagRepository): TagCreateUseCase {
        return TagCreateUseCase(repository)
    }
}
