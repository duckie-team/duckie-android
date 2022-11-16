package land.sungbin.androidprojecttemplate.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import land.sungbin.androidprojecttemplate.data.datasource.remote.RemoteSettingDataSource
import land.sungbin.androidprojecttemplate.data.datasource.remote.RemoteSettingDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun provideRemoteSettingDataSource(
        remoteSettingDataSourceImpl: RemoteSettingDataSourceImpl
    ): RemoteSettingDataSource
}
