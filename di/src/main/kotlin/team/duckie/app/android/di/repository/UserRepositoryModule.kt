/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.repository

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import team.duckie.app.android.data.user.repository.KakaoRepositoryImpl
import team.duckie.app.android.domain.user.repository.KakaoRepository

@Module
@InstallIn(ActivityComponent::class)
object UserRepositoryModule {
    @Provides
    fun provideKakaoLoginRepository(activityContext: Activity): KakaoRepository {
        return KakaoRepositoryImpl(activityContext)
    }
}
