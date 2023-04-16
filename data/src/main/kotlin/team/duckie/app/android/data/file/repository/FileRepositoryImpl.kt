/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.file.repository

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.coroutines.awaitString
import java.io.File
import javax.inject.Inject
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.domain.file.repository.FileRepository
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe

class FileRepositoryImpl @Inject constructor(private val client: Fuel) : FileRepository {
    override suspend fun upload(file: File, type: String): String {
        val request = client
            .upload(
                path = "/files",
                method = Method.POST,
                parameters = listOf("type" to type),
            )
            .add(FileDataPart(name = "file", file = file))
            .progress { readBytes, totalBytes ->
                @AllowMagicNumber(because = "100")
                val progress = readBytes.toFloat() / totalBytes.toFloat() * 100
                println("Uploading... $progress%")
            }
        val response = request.awaitString().toStringJsonMap()
        return response["url"] ?: duckieResponseFieldNpe("url")
    }
}
