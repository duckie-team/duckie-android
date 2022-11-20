package team.duckie.app.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.data.datasource.local.LocalSettingDataSource
import team.duckie.app.data.datasource.local.LocalSettingDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun provideLocalSettingDataSource(
        localSettingDataSourceImpl: LocalSettingDataSourceImpl
    ): LocalSettingDataSource
}
