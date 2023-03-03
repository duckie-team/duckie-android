/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data._local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.data._local.DuckieDatabase.Companion.DatabaseName
import team.duckie.app.android.data.exam.dao.FavoriteExamsDao
import team.duckie.app.android.data.exam.dao.MadeExamsDao
import team.duckie.app.android.data.exam.dao.SolvedExamsDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): DuckieDatabase {
        return Room.databaseBuilder(context, DuckieDatabase::class.java, DatabaseName).build()
    }

    @Provides
    fun provideMadeExamsDao(database: DuckieDatabase): MadeExamsDao {
        return database.madeExamsDao()
    }

    @Provides
    fun provideFavoriteExamsDao(database: DuckieDatabase): FavoriteExamsDao {
        return database.favoriteExamsDao()
    }

    @Provides
    fun provideSolvedExamsDao(database: DuckieDatabase): SolvedExamsDao {
        return database.solvedExamsDao()
    }
}
