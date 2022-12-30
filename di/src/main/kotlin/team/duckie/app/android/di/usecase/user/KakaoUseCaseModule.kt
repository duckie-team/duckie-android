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
import team.duckie.app.android.domain.kakao.repository.KakaoRepository
import team.duckie.app.android.domain.kakao.usecase.KakaoLoginUseCase

@Module
@InstallIn(ActivityComponent::class)
object KakaoUseCaseModule {
    @Provides
    fun provideKakaoLoginUseCase(repository: KakaoRepository): KakaoLoginUseCase {
        return KakaoLoginUseCase(repository)
    }
}
