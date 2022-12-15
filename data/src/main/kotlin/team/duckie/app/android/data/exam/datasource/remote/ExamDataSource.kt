/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.datasource.remote

import io.ktor.client.HttpClient
import team.duckie.app.android.data.exam.model.ExamRequest
import team.duckie.app.android.data.exam.model.ExamResponse
import team.duckie.app.android.data.exam.model.TagData
import team.duckie.app.android.data.user.model.UserData
import java.util.Date
import javax.inject.Inject

class ExamDataSource @Inject constructor(
    private val client: HttpClient
) {
    suspend fun postExams(examRequest: ExamRequest): ExamResponse {
        /* val request = client
             .post {
                 url("/exams")
                 setBody(examRequest)
                 header("authorization", "AT") //TODO(Evergreen) access token 자동화 방안 마련 필요
             }
         val body: ExamResponse = request.body()
         return body*/
        println(examRequest.toString())
        return dummyResponse
    }
}

private val dummyResponse = ExamResponse(
    title = "Test Title 2",
    description = "Test Description 2",
    thumbnailUrl = "Test Image Url",
    certifyingStatement = "Test 필적 확인 문구1",
    buttonTitle = "Test Button Title 1",
    isPublic = true,
    tags = listOf(
        TagData(0, "tag1"),
        TagData(1, "tag2")
    ),
    UserData(
        id = 1,
        nickName = "goddoro",
        accountEnabled = true,
        profileUrl = "asdf",
        tier = 1,
        createdAt = Date(),
        updatedAt = Date(),
        deletedAt = null,
        bannedAt = null,
    ),
    deletedAt = null,
    id = 11,
    createdAt = Date(),
    updatedAt = Date(),
    solvedCount = 0,
    answerRate = 0.0f,
    canRetry = true,
)
