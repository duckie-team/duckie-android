/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.device

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.device.repository.DeviceRepository
import team.duckie.app.android.domain.device.usecase.DeviceRegisterUseCase

@Module
@InstallIn(SingletonComponent::class)
object DeviceUseCaseModule {
    @Provides
    fun provideDeviceRegisterUseCase(repository: DeviceRepository): DeviceRegisterUseCase {
        return DeviceRegisterUseCase(repository)
    }
}
