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

private const val Empty = ""
private const val Duckie = "Duckie"

private class StringWrapper {
    var value = Empty
        private set

    fun updateValue(value: String): StringWrapper {
        return also { this.value = value }
    }
}

class RunIfTest {
    @Test
    fun `string changed`() {
        val string = StringWrapper()

        string.runIf(string.value == Empty) {
            updateValue(Duckie)
        }

        expectThat(string.value).isEqualTo(Duckie)
    }

    @Test
    fun `string not changed`() {
        val string = StringWrapper()

        string.runIf(string.value == Duckie) {
            updateValue(Empty)
        }

        expectThat(string.value).isEqualTo(Empty)
    }
}
