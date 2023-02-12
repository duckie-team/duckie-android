/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.examInstance

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.examInstance.usecase.MakeExamInstanceSubmitUseCase
import team.duckie.app.android.domain.examInstance.usecase.MakeExamInstanceUseCase
import team.duckie.app.android.domain.examInstance.repository.ExamInstanceRepository
import team.duckie.app.android.domain.examInstance.usecase.GetExamInstanceUseCase

@Module
@InstallIn(SingletonComponent::class)
object ExamInstanceUseCaseModule {
    @Provides
    fun provideGetExamInstanceUseCase(repository: ExamInstanceRepository): GetExamInstanceUseCase {
        return GetExamInstanceUseCase(repository)
    }

    @Provides
    fun provideMakeExamInstanceSubmitUseCase(
        repository: ExamInstanceRepository,
    ): MakeExamInstanceSubmitUseCase {
        return MakeExamInstanceSubmitUseCase(repository)
    }

    @Provides
    fun provideMakeExamInstanceUseCase(repository: ExamInstanceRepository): MakeExamInstanceUseCase {
        return MakeExamInstanceUseCase(repository)
    }
}
