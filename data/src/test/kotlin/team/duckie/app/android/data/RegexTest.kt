/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class RegexTest {

    private val versionRegex = Regex("\\d+\\.\\d+\\.\\d+")

    @Test
    fun `version name with characters must consist of numbers and periods`() {
        val versionName = "MVP-1.0.1"
        val expectVersionName = "1.0.1"
        val versionNameNumber = versionRegex.findAll(versionName).map { it.value }.first()
        expectThat(versionNameNumber).isEqualTo(expectVersionName)
    }

    @Test
    fun `version name with double digit number must consist of numbers and periods`() {
        val versionName = "11.0.1"
        val expectVersionName = "11.0.1"
        val versionNameNumber = versionRegex.findAll(versionName).map { it.value }.first()
        expectThat(versionNameNumber).isEqualTo(expectVersionName)
    }
}
