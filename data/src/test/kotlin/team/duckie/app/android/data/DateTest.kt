/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("NonAsciiCharacters")

package team.duckie.app.android.data

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
        val actual = "2022-11-28T06:37:39.915Z".toDate()

        val expected = buildDate(
            year = 2022,
            month = 10,
            day = 28,
            hour = 6,
            minute = 37,
            second = 39,
            millisecond = 915,
        )

        expectThat(actual).withNotNull {
            val expectCal = Calendar.getInstance().apply { time = subject }
            val actualCal = Calendar.getInstance().apply { time = expected }

            get { expectCal.get(Calendar.YEAR) } isEqualTo actualCal.get(Calendar.YEAR)
            get { expectCal.get(Calendar.MONTH) } isEqualTo actualCal.get(Calendar.MONTH)
            get { expectCal.get(Calendar.DAY_OF_MONTH) } isEqualTo actualCal.get(Calendar.DAY_OF_MONTH)
            get { expectCal.get(Calendar.HOUR_OF_DAY) } isEqualTo actualCal.get(Calendar.HOUR_OF_DAY)
            get { expectCal.get(Calendar.MINUTE) } isEqualTo actualCal.get(Calendar.MINUTE)
            get { expectCal.get(Calendar.SECOND) } isEqualTo actualCal.get(Calendar.SECOND)
            get { expectCal.get(Calendar.MILLISECOND) } isEqualTo actualCal.get(Calendar.MILLISECOND)
        }
    }
}
