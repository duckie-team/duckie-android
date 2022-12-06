/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import team.duckie.app.android.data.exam.model.QuestionRequest.Text.text
import team.duckie.app.android.data.exam.model.QuestionRequest.Text.type

data class ExamRequest(
    val title: String,
    val description: String,
    val mainTag: Tag,
    val subTag: List<Tag>,
    val certifyingStatement: String,
    val thumbnailImageUrl: String,
    //val problems
)

sealed class QuestionRequest(
    val type: String,
    val text: String,
){
    object Text: QuestionRequest(type, text)
    data class Image(val imageUrl: String) : QuestionRequest(type,text)
    data class Audio(val audioUrl: String) : QuestionRequest(type,text)
    data class Video(val videoUrl: String) : QuestionRequest(type,text)
}

data class Tag(
    val tag: Int,
    val name: String
)

data class ProblemItem(
    val type: String
)
