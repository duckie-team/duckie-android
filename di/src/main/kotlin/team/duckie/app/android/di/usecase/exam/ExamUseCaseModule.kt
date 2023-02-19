/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.exam

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.domain.exam.usecase.GetExamThumbnailUseCase
import team.duckie.app.android.domain.exam.usecase.GetExamUseCase
import team.duckie.app.android.domain.exam.usecase.MakeExamUseCase

@Module
@InstallIn(SingletonComponent::class)
object ExamUseCaseModule {
    @Provides
    fun provideMakeExamUseCase(repository: ExamRepository): MakeExamUseCase {
        return MakeExamUseCase(repository)
    }

    @Provides
    fun provideGetExamUseCase(repository: ExamRepository): GetExamUseCase {
        return GetExamUseCase(repository)
    }

    @Provides
    fun provideGetExamThumbnailUseCase(repository: ExamRepository): GetExamThumbnailUseCase {
        return GetExamThumbnailUseCase(repository)
    }
}
