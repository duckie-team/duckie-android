/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.util_test

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import team.duckie.app.android.data._util.toStringJsonMap

class JsonMappingTest {
    @Test
    fun mapping2map() {
        val json = "{\"name\":\"duckie\"}"
        val map = json.toStringJsonMap()
        expectThat(map["name"]).isNotNull().isEqualTo("duckie")
    }
}
