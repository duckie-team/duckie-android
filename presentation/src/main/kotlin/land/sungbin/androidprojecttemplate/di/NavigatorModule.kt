package land.sungbin.androidprojecttemplate.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import land.sungbin.androidprojecttemplate.ui.navigator.DuckieNavigator
import land.sungbin.androidprojecttemplate.ui.navigator.DuckieNavigatorImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigatorModule {

    @Binds
    abstract fun provideDuckieNavigator(
        navigator: DuckieNavigatorImpl
    ): DuckieNavigator
}