/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data

import io.ktor.client.HttpClient
import org.junit.Rule
import team.duckie.app.android.data.rule.HttpClientTestRule

/** API 테스트가 필요한 경우 반드시 상속 받아야 하는 클래스 */
open class ApiTest(val isMock: Boolean, val client: HttpClient) {
    @get:Rule
    val httpClientTestRule = HttpClientTestRule(
        if (isMock) client else null
    )
}
