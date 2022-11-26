/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.repository

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import team.duckie.app.android.data.gallery.repository.GalleryRepositoryImpl
import team.duckie.app.android.domain.gallery.repository.GalleryRepository

@Module
@InstallIn(ActivityComponent::class)
object GalleryRepositoryModule {
    @Provides
    fun provideGalleryRepository(@ApplicationContext context: Context): GalleryRepository {
        return GalleryRepositoryImpl(context)
    }
}
