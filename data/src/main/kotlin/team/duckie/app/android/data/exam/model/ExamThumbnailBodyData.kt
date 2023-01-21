/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model


import com.fasterxml.jackson.annotation.JsonProperty

data class ExamThumbnailBodyData(
    @JsonProperty("category")
    val category: String?,

    @JsonProperty("certifyingStatement")
    val certifyingStatement: String?,

    @JsonProperty("mainTag")
    val mainTag: String?,

    @JsonProperty("nickName")
    val nickName: String?,

    @JsonProperty("title")
    val title: String?,

    @JsonProperty("type")
    val type: String?
)
