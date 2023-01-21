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
import team.duckie.app.android.data.device.repository.DeviceRepositoryImpl
import team.duckie.app.android.data.exam.repository.ExamRepositoryImpl
import team.duckie.app.android.data.file.repository.FileRepositoryImpl
import team.duckie.app.android.data.follow.repository.FollowsRepositoryImpl
import team.duckie.app.android.data.gallery.repository.GalleryRepositoryImpl
import team.duckie.app.android.data.search.repository.SearchRepositoryImpl
import team.duckie.app.android.data.tag.repository.TagRepositoryImpl
import team.duckie.app.android.data.terms.repository.TermsRepositoryImpl
import team.duckie.app.android.data.user.repository.UserRepositoryImpl
import team.duckie.app.android.domain.auth.repository.AuthRepository
import team.duckie.app.android.domain.category.repository.CategoryRepository
import team.duckie.app.android.domain.device.repository.DeviceRepository
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.domain.file.repository.FileRepository
import team.duckie.app.android.domain.follow.repository.FollowsRepository
import team.duckie.app.android.domain.gallery.repository.GalleryRepository
import team.duckie.app.android.domain.search.repository.SearchRepository
import team.duckie.app.android.domain.tag.repository.TagRepository
import team.duckie.app.android.domain.terms.repository.TermsRepository
import team.duckie.app.android.domain.user.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    abstract fun provdeDeviceRepository(impl: DeviceRepositoryImpl): DeviceRepository

    @Binds
    abstract fun provideExamRepository(impl: ExamRepositoryImpl): ExamRepository

    @Binds
    abstract fun provideFileRepository(impl: FileRepositoryImpl): FileRepository

    @Binds
    abstract fun provideFollowsRepository(impl: FollowsRepositoryImpl): FollowsRepository

    @Binds
    abstract fun provideGalleryRepository(impl: GalleryRepositoryImpl): GalleryRepository

    @Binds
    abstract fun provideTagRepository(impl: TagRepositoryImpl): TagRepository

    @Binds
    abstract fun provideTermsRepository(impl: TermsRepositoryImpl): TermsRepository

    @Binds
    abstract fun provideUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideSearchRepository(impl: SearchRepositoryImpl): SearchRepository
}
