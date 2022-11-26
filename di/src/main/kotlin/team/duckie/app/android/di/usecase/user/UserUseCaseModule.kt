/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.user

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import team.duckie.app.android.domain.user.repository.KakaoLoginRepository
import team.duckie.app.android.domain.user.usecase.KakaoLoginUseCase

@Module
@InstallIn(ActivityComponent::class)
object UserUseCaseModule {
    @Provides
    fun provideKakaoLoginUseCase(repository: KakaoLoginRepository): KakaoLoginUseCase {
        return KakaoLoginUseCase(repository)
    }
}
