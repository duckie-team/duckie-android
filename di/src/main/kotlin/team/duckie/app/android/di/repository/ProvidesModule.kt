/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.di.repository

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.data.gallery.repository.GalleryRepositoryImpl
import team.duckie.app.android.data.kakao.repository.KakaoRepositoryImpl
import team.duckie.app.android.data.recommendation.repository.RecommendationRepositoryImpl
import team.duckie.app.android.domain.gallery.repository.GalleryRepository
import team.duckie.app.android.domain.kakao.repository.KakaoRepository
import team.duckie.app.android.domain.recommendation.repository.RecommendationRepository

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {
    @Provides
    fun provideGalleryRepository(@ApplicationContext context: Context): GalleryRepository {
        return GalleryRepositoryImpl(context)
    }

    @Provides
    fun provideKakaoLoginRepository(activityContext: Activity): KakaoRepository {
        return KakaoRepositoryImpl(activityContext)
    }

    @Provides
    fun provideRecommendationRepository(@ApplicationContext context: Context): RecommendationRepository {
        return RecommendationRepositoryImpl(context)
    }
}
