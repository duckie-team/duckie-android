package team.duckie.app.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.data.datasource.remote.RemoteSettingDataSource
import team.duckie.app.data.datasource.remote.RemoteSettingDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun provideRemoteSettingDataSource(
        remoteSettingDataSourceImpl: RemoteSettingDataSourceImpl
    ): RemoteSettingDataSource
}
