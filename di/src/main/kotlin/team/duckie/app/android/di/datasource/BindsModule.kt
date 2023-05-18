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
import team.duckie.app.android.data.ignore.datasource.IgnoreRemoteDataSource
import team.duckie.app.android.data.ignore.datasource.IgnoreRemoteDataSourceImpl
import team.duckie.app.android.data.notification.datasource.NotificationDataSource
import team.duckie.app.android.data.notification.datasource.NotificationRemoteDataSourceImpl
import team.duckie.app.android.data.quiz.datasource.QuizDataSource
import team.duckie.app.android.data.quiz.datasource.QuizRemoteDataSourceImpl
import team.duckie.app.android.data.ranking.datasource.RankingDataSource
import team.duckie.app.android.data.ranking.datasource.RankingRemoteDataSourceImpl
import team.duckie.app.android.data.report.datasource.ReportRemoteDataSource
import team.duckie.app.android.data.report.datasource.ReportRemoteDataSourceImpl
import team.duckie.app.android.data.search.datasource.SearchLocalDataSource
import team.duckie.app.android.data.search.datasource.SearchLocalDataSourceImpl
import team.duckie.app.android.data.search.datasource.SearchRemoteDataSource
import team.duckie.app.android.data.search.datasource.SearchRemoteDataSourceImpl
import team.duckie.app.android.data.user.datasource.UserDataSource
import team.duckie.app.android.data.user.datasource.UserRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Singleton
    @Binds
    abstract fun provideAuthRemoteDataSource(impl: AuthRemoteDataSourceImpl): AuthDataSource

    @Singleton
    @Binds
    abstract fun provideUserRemoteDataSourceImpl(impl: UserRemoteDataSourceImpl): UserDataSource

    @Singleton
    @Binds
    abstract fun provideExamInfoLocalDataSource(impl: ExamInfoLocalDataSourceImpl): ExamInfoDataSource

    @Singleton
    @Binds
    abstract fun provideSearchLocalDataSource(impl: SearchLocalDataSourceImpl): SearchLocalDataSource

    @Singleton
    @Binds
    abstract fun provideSearchRemoteDataSource(impl: SearchRemoteDataSourceImpl): SearchRemoteDataSource

    @Singleton
    @Binds
    abstract fun provideRankingRemoteDataSource(impl: RankingRemoteDataSourceImpl): RankingDataSource

    @Singleton
    @Binds
    abstract fun provideNotificationRemoteDataSource(impl: NotificationRemoteDataSourceImpl): NotificationDataSource

    @Singleton
    @Binds
    abstract fun provideReportRemoteDataSource(impl: ReportRemoteDataSourceImpl): ReportRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindsQuizRemoteDataSource(impl: QuizRemoteDataSourceImpl): QuizDataSource

    @Singleton
    @Binds
    abstract fun bindIgnoreRemoteDataSource(impl: IgnoreRemoteDataSourceImpl): IgnoreRemoteDataSource
}
