/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.navigator.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.feature.ui.home.navigator.impl.HomeNavigatorImpl
import team.duckie.app.android.navigator.feature.home.HomeNavigator

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HomeNavigatorModule {

    @Binds
    abstract fun bindHomeNavigator(navigator: HomeNavigatorImpl): HomeNavigator
}
