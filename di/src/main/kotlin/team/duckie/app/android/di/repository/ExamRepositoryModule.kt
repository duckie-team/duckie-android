/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.data.exam.repository.ExamRepositoryImpl
import team.duckie.app.android.domain.exam.repository.ExamRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class ExamRepositoryModule {

    @Binds
    abstract fun provideExamRepository(
        examRepositoryImpl: ExamRepositoryImpl,
    ): ExamRepository

}
