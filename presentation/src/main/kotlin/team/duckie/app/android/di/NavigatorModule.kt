package team.duckie.app.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.ui.navigator.DuckieNavigator
import team.duckie.app.ui.navigator.DuckieNavigatorImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigatorModule {

    @Binds
    abstract fun provideDuckieNavigator(
        navigator: DuckieNavigatorImpl
    ): DuckieNavigator
}
