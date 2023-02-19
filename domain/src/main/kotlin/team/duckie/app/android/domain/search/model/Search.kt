/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.search.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User

@Immutable
sealed class Search(val type: String) {
    companion object {
        const val Exams = "EXAMS"
        const val Users = "USERS"
        const val Tags = "TAGS"
    }

    @Immutable
    data class ExamSearch(
        val exams: List<Exam>,
    ) : Search(Exams)

    @Immutable
    data class UserSearch(
        val users: List<User>,
    ) : Search(Users)

    @Immutable
    data class TagSearch(
        val tags: List<Tag>,
    ) : Search(Tags)
}
