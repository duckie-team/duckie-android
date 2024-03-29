/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.di.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.data.auth.repository.AuthRepositoryImpl
import team.duckie.app.android.data.category.repository.CategoryRepositoryImpl
import team.duckie.app.android.data.challengecomment.repository.ChallengeCommentRepositoryImpl
import team.duckie.app.android.data.device.repository.DeviceRepositoryImpl
import team.duckie.app.android.data.exam.repository.ExamRepositoryImpl
import team.duckie.app.android.data.examInstance.repository.ExamInstanceRepositoryImpl
import team.duckie.app.android.data.file.repository.FileRepositoryImpl
import team.duckie.app.android.data.follow.repository.FollowRepositoryImpl
import team.duckie.app.android.data.gallery.repository.GalleryRepositoryImpl
import team.duckie.app.android.data.heart.repository.HeartRepositoryImpl
import team.duckie.app.android.data.home.repository.HomeRepositoryImpl
import team.duckie.app.android.data.ignore.repository.IgnoreRepositoryImpl
import team.duckie.app.android.data.me.repository.MeRepositoryImpl
import team.duckie.app.android.data.notification.repository.NotificationRepositoryImpl
import team.duckie.app.android.data.problem.repository.ProblemRepositoryImpl
import team.duckie.app.android.data.quiz.repository.QuizRepositoryImpl
import team.duckie.app.android.data.ranking.repository.RankingRepositoryImpl
import team.duckie.app.android.data.recommendation.repository.RecommendationRepositoryImpl
import team.duckie.app.android.data.report.repository.ReportRepositoryImpl
import team.duckie.app.android.data.search.repository.SearchRepositoryImpl
import team.duckie.app.android.data.tag.repository.TagRepositoryImpl
import team.duckie.app.android.data.terms.repository.TermsRepositoryImpl
import team.duckie.app.android.data.user.repository.UserRepositoryImpl
import team.duckie.app.android.domain.auth.repository.AuthRepository
import team.duckie.app.android.domain.category.repository.CategoryRepository
import team.duckie.app.android.domain.challengecomment.repository.ChallengeCommentRepository
import team.duckie.app.android.domain.device.repository.DeviceRepository
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.domain.examInstance.repository.ExamInstanceRepository
import team.duckie.app.android.domain.file.repository.FileRepository
import team.duckie.app.android.domain.follow.repository.FollowRepository
import team.duckie.app.android.domain.gallery.repository.GalleryRepository
import team.duckie.app.android.domain.heart.repository.HeartRepository
import team.duckie.app.android.domain.home.repository.HomeRepository
import team.duckie.app.android.domain.ignore.repository.IgnoreRepository
import team.duckie.app.android.domain.me.MeRepository
import team.duckie.app.android.domain.notification.repository.NotificationRepository
import team.duckie.app.android.domain.problem.repository.ProblemRepository
import team.duckie.app.android.domain.quiz.repository.QuizRepository
import team.duckie.app.android.domain.ranking.repository.RankingRepository
import team.duckie.app.android.domain.recommendation.repository.RecommendationRepository
import team.duckie.app.android.domain.report.repository.ReportRepository
import team.duckie.app.android.domain.search.repository.SearchRepository
import team.duckie.app.android.domain.tag.repository.TagRepository
import team.duckie.app.android.domain.terms.repository.TermsRepository
import team.duckie.app.android.domain.user.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Singleton
    @Binds
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun provideCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @Singleton
    @Binds
    abstract fun provdeDeviceRepository(impl: DeviceRepositoryImpl): DeviceRepository

    @Singleton
    @Binds
    abstract fun provideExamRepository(impl: ExamRepositoryImpl): ExamRepository

    @Singleton
    @Binds
    abstract fun provideExamInstanceRepository(impl: ExamInstanceRepositoryImpl): ExamInstanceRepository

    @Singleton
    @Binds
    abstract fun provideRecommendationRepository(impl: RecommendationRepositoryImpl): RecommendationRepository

    @Singleton
    @Binds
    abstract fun provideFileRepository(impl: FileRepositoryImpl): FileRepository

    @Singleton
    @Binds
    abstract fun provideFollowsRepository(impl: FollowRepositoryImpl): FollowRepository

    @Singleton
    @Binds
    abstract fun provideHeartRepository(impl: HeartRepositoryImpl): HeartRepository

    @Singleton
    @Binds
    abstract fun provideMeRepository(impl: MeRepositoryImpl): MeRepository

    @Singleton
    @Binds
    abstract fun provideGalleryRepository(impl: GalleryRepositoryImpl): GalleryRepository

    @Singleton
    @Binds
    abstract fun provideTagRepository(impl: TagRepositoryImpl): TagRepository

    @Singleton
    @Binds
    abstract fun provideTermsRepository(impl: TermsRepositoryImpl): TermsRepository

    @Singleton
    @Binds
    abstract fun provideUserRepository(impl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun provideSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @Singleton
    @Binds
    abstract fun provideRankingRepository(impl: RankingRepositoryImpl): RankingRepository

    @Singleton
    @Binds
    abstract fun provideNotificationRepository(impl: NotificationRepositoryImpl): NotificationRepository

    @Singleton
    @Binds
    abstract fun provideReportRepository(impl: ReportRepositoryImpl): ReportRepository

    @Singleton
    @Binds
    abstract fun bindsQuizRepository(impl: QuizRepositoryImpl): QuizRepository

    @Singleton
    @Binds
    abstract fun bindIgnoreRepository(impl: IgnoreRepositoryImpl): IgnoreRepository

    @Singleton
    @Binds
    abstract fun bindChallengeCommentRepository(impl: ChallengeCommentRepositoryImpl): ChallengeCommentRepository

    @Singleton
    @Binds
    abstract fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository

    @Singleton
    @Binds
    abstract fun bindProblemRepository(impl: ProblemRepositoryImpl): ProblemRepository
}
