package team.duckie.app.android.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import team.duckie.app.data.datasource.local.room.DuckieDataBase
import team.duckie.app.data.datasource.local.room.SettingDao

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    
    @Provides
    fun provideDuckieDatabase(
        @ApplicationContext context: Context,
    ): DuckieDataBase = synchronized(
        lock = this,
    ) {
        Room
            .databaseBuilder(context, DuckieDataBase::class.java, "DuckieDataBase")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideSettingDao(
        duckieDataBase: DuckieDataBase,
    ): SettingDao = duckieDataBase.settingDao()
}
