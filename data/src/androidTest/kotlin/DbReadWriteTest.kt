/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.contains
import team.duckie.app.android.data._local.DuckieDatabase
import team.duckie.app.android.data.exam.dao.FavoriteExamsDao
import team.duckie.app.android.data.exam.model.ExamInfoEntity

@RunWith(AndroidJUnit4ClassRunner::class)
class ExamEntityReadWriteTest {
    private lateinit var favoriteExamsDao: FavoriteExamsDao
    private lateinit var db: DuckieDatabase

    @Before
    fun setupDataBase() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            DuckieDatabase::class.java
        ).allowMainThreadQueries().build()
        favoriteExamsDao = db.favoriteExamsDao()
    }

    @Test
    fun insertAll_returns_True(): Unit = runBlocking {
        val examInfo = ExamInfoEntity(
            id = 1,
            title = "재웅 덕질고사",
            thumbnailUrl = "",
        )
        favoriteExamsDao.insertAll(examInfo)
        val result = favoriteExamsDao.getAll()
        expectThat(result).contains(examInfo)
    }

    @After
    fun closeDataBase() {
        db.close()
    }
}
