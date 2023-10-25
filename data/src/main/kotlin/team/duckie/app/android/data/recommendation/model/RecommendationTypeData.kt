/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.recommendation.model

enum class RecommendationTypeData(
    val value: String,
) {
    NONE(""),
    MUSIC("Music"),
    ;

    companion object {
        fun from(value: String) = values().first { it.value == value }
    }
}
