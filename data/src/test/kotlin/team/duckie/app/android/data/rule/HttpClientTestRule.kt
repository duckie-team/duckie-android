/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.rule

import io.ktor.client.HttpClient
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._datasource.updateClient

class HttpClientTestRule(private val mockClient: HttpClient) : TestWatcher() {
    private lateinit var originalClient: HttpClient

    override fun starting(description: Description?) {
        originalClient = client
        updateClient(mockClient)
        super.starting(description)
    }

    override fun finished(description: Description?) {
        updateClient(originalClient)
        super.finished(description)
    }
}
