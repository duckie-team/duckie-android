package land.sungbin.androidprojecttemplate.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import land.sungbin.androidprojecttemplate.data.datasource.local.LocalSettingDataSource
import land.sungbin.androidprojecttemplate.data.datasource.local.LocalSettingDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun provideLocalSettingDataSource(
        localSettingDataSourceImpl: LocalSettingDataSourceImpl
    ): LocalSettingDataSource
}
