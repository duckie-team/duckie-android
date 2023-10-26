/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.heart.model.HeartData
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.model.UserResponse

internal data class ExamData(
    @field:JsonProperty("id")
    val id: Int? = null,

    @field:JsonProperty("title")
    val title: String? = null,

    @field:JsonProperty("description")
    val description: String? = null,

    @field:JsonProperty("thumbnailUrl")
    val thumbnailUrl: String? = null,

    @field:JsonProperty("thumbnailType")
    val thumbnailType: String? = null,

    @field:JsonProperty("type")
    val type: String? = null,

    @field:JsonProperty("buttonTitle")
    val buttonTitle: String? = null,

    @field:JsonProperty("certifyingStatement")
    val certifyingStatement: String? = null,

    @field:JsonProperty("solvedCount")
    val solvedCount: Int? = null,

    @field:JsonProperty("answerRate")
    val answerRate: Float? = null,

    @field:JsonProperty("user")
    val user: UserResponse? = null,

    @field:JsonProperty("category")
    val category: CategoryData? = null,

    @field:JsonProperty("mainTag")
    val mainTag: TagData? = null,

    @field:JsonProperty("subTags")
    val subTags: List<TagData>? = null,

    @field:JsonProperty("status")
    val status: String? = null,

    @field:JsonProperty("heart")
    val heart: HeartData? = null,

    @field:JsonProperty("heartCount")
    val heartCount: Int? = null,

    @field:JsonProperty("myRecord")
    val myRecord: QuizInfoResponse? = null,

    @field:JsonProperty("myMusicRecord")
    val myMusicRecord: MyMusicRecordData? = null,

    @field:JsonProperty("challenges")
    val quizs: List<QuizInfoResponse>? = null,

    @field:JsonProperty("perfectScoreImageUrl")
    val perfectScoreImageUrl: String? = null,

    @field:JsonProperty("problems")
    val problems: List<ProblemData>? = null,

    @field:JsonProperty("timer")
    val timer: Int? = null,

    @field:JsonProperty("requirementQuestion")
    val requirementQuestion: String? = null,

    @field:JsonProperty("requirementPlaceholder")
    val requirementPlaceholder: String? = null,

    @field:JsonProperty("problemCount")
    val problemCount: Int? = null,

    @field:JsonProperty("totalProblemCount")
    val totalProblemCount: Int? = null,

    @field:JsonProperty("contributors")
    val contributors: List<UserResponse>? = null,

    @field:JsonProperty("contributorCount")
    val contributorCount: Int? = null,

    @field:JsonProperty("sampleQuestion")
    val sampleQuestion: List<QuestionData>? = null,

    @field:JsonProperty("examInstances")
    val musicExamInstances: List<MusicExamInstanceData>? = null,
)

internal data class ExamsData(
    @field:JsonProperty("exams")
    val exams: List<ExamData>? = null,
)
