/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("NonAsciiCharacters", "SameParameterValue")

package team.duckie.app.android.data.util_test

import java.util.Calendar
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.withNotNull
import team.duckie.app.android.data._util.toDate
import team.duckie.app.android.data.util.buildDate

class DateTest {
    @Test
    fun `ISO 8601 date 변환`() {
        val isoString = "2022-11-28T06:37:39.915Z".toDate()
        val isoDate = buildDate(
            year = 2022,
            month = 10,
            day = 28,
            hour = 6,
            minute = 37,
            second = 39,
            millisecond = 915,
        )

        expectThat(isoString).withNotNull {
            val expect = Calendar.getInstance().apply { time = subject }
            val actual = Calendar.getInstance().apply { time = isoDate }

            get { expect.get(Calendar.YEAR) } isEqualTo actual.get(Calendar.YEAR)
            get { expect.get(Calendar.MONTH) } isEqualTo actual.get(Calendar.MONTH)
            get { expect.get(Calendar.DAY_OF_MONTH) } isEqualTo actual.get(Calendar.DAY_OF_MONTH)
            get { expect.get(Calendar.HOUR_OF_DAY) } isEqualTo actual.get(Calendar.HOUR_OF_DAY)
            get { expect.get(Calendar.MINUTE) } isEqualTo actual.get(Calendar.MINUTE)
            get { expect.get(Calendar.SECOND) } isEqualTo actual.get(Calendar.SECOND)
            get { expect.get(Calendar.MILLISECOND) } isEqualTo actual.get(Calendar.MILLISECOND)
        }
    }
}
