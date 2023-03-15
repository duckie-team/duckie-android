/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.datasource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.data.auth.datasource.AuthDataSource
import team.duckie.app.android.data.auth.datasource.AuthRemoteDataSourceImpl
import team.duckie.app.android.data.exam.datasource.ExamInfoDataSource
import team.duckie.app.android.data.exam.datasource.ExamInfoLocalDataSourceImpl
import team.duckie.app.android.data.search.datasource.SearchLocalDataSource
import team.duckie.app.android.data.search.datasource.SearchLocalDataSourceImpl
import team.duckie.app.android.data.search.datasource.SearchRemoteDataSource
import team.duckie.app.android.data.search.datasource.SearchRemoteDataSourceImpl
import team.duckie.app.android.data.user.datasource.UserDataSource
import team.duckie.app.android.data.user.datasource.UserRemoteDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    abstract fun provideAuthRemoteDataSource(impl: AuthRemoteDataSourceImpl): AuthDataSource

    @Binds
    abstract fun provideUserRemoteDataSourceImpl(impl: UserRemoteDataSourceImpl): UserDataSource

    @Binds
    abstract fun provideExamInfoLocalDataSource(impl: ExamInfoLocalDataSourceImpl): ExamInfoDataSource

    @Binds
    abstract fun provideSearchLocalDataSource(impl: SearchLocalDataSourceImpl): SearchLocalDataSource

    @Binds
    abstract fun provideSearchRemoteDataSource(impl: SearchRemoteDataSourceImpl): SearchRemoteDataSource
}
