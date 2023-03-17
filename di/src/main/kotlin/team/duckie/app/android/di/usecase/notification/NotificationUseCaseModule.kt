/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.notification

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.notification.repository.NotificationRepository
import team.duckie.app.android.domain.notification.usecase.DeleteNotificationUseCase
import team.duckie.app.android.domain.notification.usecase.GetNotificationsUseCase

@Module
@InstallIn(SingletonComponent::class)
object NotificationUseCaseModule {

    @Provides
    fun provideGetNotificationsUseCase(repository: NotificationRepository): GetNotificationsUseCase {
        return GetNotificationsUseCase(repository)
    }

    @Provides
    fun providesDeleteNotificationUseCase(repository: NotificationRepository): DeleteNotificationUseCase {
        return DeleteNotificationUseCase(repository)
    }
}
