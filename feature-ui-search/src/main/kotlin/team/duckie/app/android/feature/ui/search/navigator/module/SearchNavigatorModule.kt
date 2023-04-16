/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.navigator.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.feature.ui.search.navigator.impl.SearchNavigatorImpl
import team.duckie.app.android.navigator.feature.search.SearchNavigator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SearchNavigatorModule {

    @Singleton
    @Binds
    abstract fun bindSearchNavigator(navigator: SearchNavigatorImpl): SearchNavigator
}
