/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.datasource

import team.duckie.app.android.data.exam.dao.FavoriteExamsDao
import team.duckie.app.android.data.exam.dao.MadeExamsDao
import team.duckie.app.android.data.exam.dao.RecentExamsDao
import team.duckie.app.android.data.exam.dao.SolvedExamsDao
import team.duckie.app.android.data.exam.model.ExamInfoEntity
import javax.inject.Inject

class ExamInfoLocalDataSourceImpl @Inject constructor(
    private val favoriteExamsDao: FavoriteExamsDao,
    private val madeExamsDao: MadeExamsDao,
    private val solvedExamsDao: SolvedExamsDao,
    private val recentExamsDao: RecentExamsDao,
) : ExamInfoDataSource {
    override suspend fun getFavoriteExams(): List<ExamInfoEntity> {
        return favoriteExamsDao.getAll()
    }

    override suspend fun getMadeExams(): List<ExamInfoEntity> {
        return madeExamsDao.getAll()
    }

    override suspend fun getSolvedExams(): List<ExamInfoEntity> {
        return solvedExamsDao.getAll()
    }

    override suspend fun getRecentExams(): List<ExamInfoEntity> {
        return recentExamsDao.getAll()
    }
}
