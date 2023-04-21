/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.navigator.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.feature.ui.profile.navigator.impl.ProfileEditNavigatorImpl
import team.duckie.app.android.feature.ui.profile.navigator.impl.ProfileNavigatorImpl
import team.duckie.app.android.navigator.feature.profile.ProfileEditNavigator
import team.duckie.app.android.navigator.feature.profile.ProfileNavigator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ProfileNavigatorModule {

    @Singleton
    @Binds
    abstract fun bindProfileNavigator(navigator: ProfileNavigatorImpl): ProfileNavigator

    @Singleton
    @Binds
    abstract fun bindProfileEditNavigator(navigator: ProfileEditNavigatorImpl): ProfileEditNavigator
}
