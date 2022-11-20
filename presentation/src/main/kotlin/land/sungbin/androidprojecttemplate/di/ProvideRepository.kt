package land.sungbin.androidprojecttemplate.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import land.sungbin.androidprojecttemplate.data.datasource.local.LocalSettingDataSource
import land.sungbin.androidprojecttemplate.data.datasource.remote.RemoteSettingDataSource
import land.sungbin.androidprojecttemplate.data.repository.SettingRepositoryImpl
import land.sungbin.androidprojecttemplate.domain.repository.SettingRepository

@Module
@InstallIn(SingletonComponent::class)
internal object ProvideRepository {

    @Provides
    fun provideSettingRepository(
        @ApplicationContext context: Context,
        localSettingDatasource: LocalSettingDataSource,
        remoteSettingDatasource: RemoteSettingDataSource,
    ): SettingRepository {
        return SettingRepositoryImpl(
            isOfflineMode = !isNetworkAvailable(context = context),
            localSettingDataSource = localSettingDatasource,
            remoteSettingDataSource = remoteSettingDatasource,
        )
    }

    private fun isNetworkAvailable(context: Context) =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }
}
