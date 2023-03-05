/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.dao

import androidx.room.Dao
import androidx.room.Query
import team.duckie.app.android.data._local.BaseDao
import team.duckie.app.android.data.exam.model.ExamInfoEntity

@Dao
interface SolvedExamsDao : BaseDao<ExamInfoEntity> {

    @Query("SELECT * FROM exam_infos")
    suspend fun getAll(): List<ExamInfoEntity>
}
