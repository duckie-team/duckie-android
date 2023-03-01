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
import team.duckie.app.android.data.exam.dao.SolvedExamsDao
import team.duckie.app.android.data.exam.model.ExamInfoEntity

@Database(
    entities = [
        ExamInfoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DuckieDatabase : RoomDatabase() {
    abstract fun favoriteExamsDao(): FavoriteExamsDao
    abstract fun madeExamsDao(): MadeExamsDao
    abstract fun solvedExamsDao(): SolvedExamsDao

    companion object{
        const val DatabaseName = "duckie-database"
    }
}
