/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.android.feature.createproblem

enum class CreateProblemType(val key: String, val minCount: Int, val maxCount: Int) {
    Problem("Problem", 1, 1),
    Exam("Exam", 5, 10),
}
