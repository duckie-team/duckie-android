/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data._local

import androidx.room.Database
import androidx.room.RoomDatabase
import team.duckie.app.android.data.exam.dao.FavoriteExamsDao
import team.duckie.app.android.data.exam.dao.MadeExamsDao
import team.duckie.app.android.data.exam.dao.RecentExamsDao
import team.duckie.app.android.data.exam.dao.SolvedExamsDao
import team.duckie.app.android.data.exam.model.ExamInfoEntity
import team.duckie.app.android.data.search.dao.SearchDao
import team.duckie.app.android.data.tag.model.SearchEntity

@Database(
    entities = [
        ExamInfoEntity::class,
        SearchEntity::class,
    ],
    version = 2,
    exportSchema = true,
)
abstract class DuckieDatabase : RoomDatabase() {
    abstract fun favoriteExamsDao(): FavoriteExamsDao
    abstract fun madeExamsDao(): MadeExamsDao
    abstract fun solvedExamsDao(): SolvedExamsDao

    abstract fun recentExamsDao(): RecentExamsDao

    abstract fun searchDao(): SearchDao

    companion object {
        const val DatabaseName = "duckie-database"
    }
}
