/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.di.repository

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.data.kakao.repository.KakaoRepositoryImpl
import team.duckie.app.android.domain.kakao.repository.KakaoRepository

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {
    @Provides
    fun provideKakaoLoginRepository(activityContext: Activity): KakaoRepository {
        return KakaoRepositoryImpl(activityContext)
    }
}
