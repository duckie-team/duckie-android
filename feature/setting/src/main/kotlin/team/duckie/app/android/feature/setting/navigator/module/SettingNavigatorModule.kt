/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.navigator.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.feature.setting.navigator.impl.SettingNavigatorImpl
import team.duckie.app.android.navigator.feature.setting.SettingNavigator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SettingNavigatorModule {

    @Singleton
    @Binds
    abstract fun bindSettingNavigator(navigator: SettingNavigatorImpl): SettingNavigator
}
