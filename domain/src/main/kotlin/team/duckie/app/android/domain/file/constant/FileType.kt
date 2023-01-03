/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.file.constant

import androidx.compose.runtime.Immutable

@Immutable
enum class FileType(val value: String) {
    Profile("profile"),
    ProblemQuestionImage("problem-question-image"),
    ProblemQuestionAudio("problem-question-audio"),
    ProblemQuestionVideo("problem-question-video"),
    ProblemAnswer("problem-answer"),
    ExamThumbnail("exam-thumbnail"),
    ExamThubnailResource("exam-thumbnail-resource"),
}
