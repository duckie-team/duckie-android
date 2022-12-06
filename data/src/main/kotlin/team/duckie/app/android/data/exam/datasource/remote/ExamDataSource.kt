/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class ExamDataSource @Inject constructor(
    private val client: HttpClient
) {

    suspend fun postExams(){
        val request = client.post {
            setBody()
            header("authorization", "accessToken")
        }
    }
}
