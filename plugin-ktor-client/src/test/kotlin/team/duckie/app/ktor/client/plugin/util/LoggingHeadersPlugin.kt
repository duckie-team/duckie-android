/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.ktor.client.plugin.util

import io.ktor.client.plugins.api.SendingRequest
import io.ktor.client.plugins.api.createClientPlugin

val LoggingRequestHeadersPlugin = createClientPlugin(
    name = "LoggingRequestHeaders",
    createConfiguration = ::LogWriterConfig,
) {
    on(SendingRequest) { request, _ ->
        request.headers.entries().forEach(pluginConfig::printHeader)
    }
}

class LogWriterConfig {
    var writer: (log: String) -> Unit = {}
}

private fun LogWriterConfig.printHeader(entry: Map.Entry<String, List<String>>) {
    var headerString = entry.key + ": "
    entry.value.forEach { headerValue ->
        headerString += "$headerValue;"
    }
    writer("-> $headerString")
}
