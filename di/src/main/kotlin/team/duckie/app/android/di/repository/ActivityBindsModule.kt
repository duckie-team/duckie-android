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
import dagger.hilt.android.components.ActivityComponent
import team.duckie.app.android.data.kakao.repository.KakaoRepositoryImpl
import team.duckie.app.android.domain.kakao.repository.KakaoRepository

@Module
@InstallIn(ActivityComponent::class)
abstract class ActivityBindsModule {

    @Binds
    abstract fun bindsKakaoRepository(repository: KakaoRepositoryImpl): KakaoRepository
}
