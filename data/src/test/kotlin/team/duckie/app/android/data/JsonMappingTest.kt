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
import strikt.assertions.isNotNull
import team.duckie.app.android.data._util.toJsonMap
import team.duckie.app.android.data._util.toStringJsonMap

class JsonMappingTest {
    @Test
    fun mapping_to_string_map() {
        val rawJson = "{\"name\":\"duckie\"}"
        val actual = rawJson.toStringJsonMap()

        val expected = "duckie"

        expectThat(actual["name"]).isNotNull().isEqualTo(expected)
    }

    @Test
    fun mapping_to_list_map() {
        val rawJson = "{\"name\":[\"duckie\"]}"
        val actual: Map<String, List<String>> = rawJson.toJsonMap()

        val expected = listOf("duckie")

        expectThat(actual["name"]).isNotNull().isEqualTo(expected)
    }
}
