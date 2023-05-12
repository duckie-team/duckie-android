/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.kotlin

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TimesTest {
    @Test
    fun `seconds to ms`() {
        // small
        expectThat(1.seconds).isEqualTo(1000L)
        expectThat(2.seconds).isEqualTo(2000L)
        expectThat(3.seconds).isEqualTo(3000L)
        expectThat(4.seconds).isEqualTo(4000L)
        expectThat(5.seconds).isEqualTo(5000L)
        expectThat(6.seconds).isEqualTo(6000L)
        expectThat(7.seconds).isEqualTo(7000L)
        expectThat(8.seconds).isEqualTo(8000L)
        expectThat(9.seconds).isEqualTo(9000L)
        expectThat(10.seconds).isEqualTo(10000L)

        // large
        expectThat(100.seconds).isEqualTo(100000L)
        expectThat(200.seconds).isEqualTo(200000L)
        expectThat(300.seconds).isEqualTo(300000L)
        expectThat(400.seconds).isEqualTo(400000L)
        expectThat(500.seconds).isEqualTo(500000L)
        expectThat(600.seconds).isEqualTo(600000L)
        expectThat(700.seconds).isEqualTo(700000L)
        expectThat(800.seconds).isEqualTo(800000L)
        expectThat(900.seconds).isEqualTo(900000L)
        expectThat(1000.seconds).isEqualTo(1000000L)
    }

    @Test
    fun `minute to ms`() {
        // small
        expectThat(1.minutes).isEqualTo(60000L)
        expectThat(2.minutes).isEqualTo(120000L)
        expectThat(3.minutes).isEqualTo(180000L)
        expectThat(4.minutes).isEqualTo(240000L)
        expectThat(5.minutes).isEqualTo(300000L)
        expectThat(6.minutes).isEqualTo(360000L)
        expectThat(7.minutes).isEqualTo(420000L)
        expectThat(8.minutes).isEqualTo(480000L)
        expectThat(9.minutes).isEqualTo(540000L)
        expectThat(10.minutes).isEqualTo(600000L)

        // large
        expectThat(100.minutes).isEqualTo(6000000L)
        expectThat(200.minutes).isEqualTo(12000000L)
        expectThat(300.minutes).isEqualTo(18000000L)
        expectThat(400.minutes).isEqualTo(24000000L)
        expectThat(500.minutes).isEqualTo(30000000L)
        expectThat(600.minutes).isEqualTo(36000000L)
        expectThat(700.minutes).isEqualTo(42000000L)
        expectThat(800.minutes).isEqualTo(48000000L)
        expectThat(900.minutes).isEqualTo(54000000L)
        expectThat(1000.minutes).isEqualTo(60000000L)
    }

    @Test
    fun `should HH,MM,SS when convert the seconds contains minute`() {
        val actual = 100.toHourMinuteSecond()
        val expected = "00:01:40"
        expectThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should HH,MM,SS when convert the seconds cointains hour`() {
        val actual = 10000.toHourMinuteSecond()
        val expected = "02:46:40"
        expectThat(actual).isEqualTo(expected)
    }
}
