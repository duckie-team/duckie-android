/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.examInstance.model

enum class ExamStatus(val text: String) {
    Ready("READY"),
    Funding("FUNDING"),
    Pending("PENDING"),
    Submitted("SUBMITTED"),
    ;

    companion object {
        fun from(text: String) = values().firstOrNull { it.text == text }
    }
}
