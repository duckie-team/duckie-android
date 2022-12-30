/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data._util

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody

internal inline fun HttpRequestBuilder.jsonBody(builder: JsonBuilder.() -> Unit) {
    setBody(buildJson(builder))
}
