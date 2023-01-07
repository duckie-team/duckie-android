/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.file

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.file.repository.FileRepository
import team.duckie.app.android.domain.file.usecase.FileUploadUseCase

@Module
@InstallIn(SingletonComponent::class)
object FileUseCaseModule {
    @Provides
    fun provideFileUploadUseCase(repository: FileRepository): FileUploadUseCase {
        return FileUploadUseCase(repository)
    }
}
