/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.android.ui.const

/** Intent.Extra Key 값 모음 */
object Extras {
    const val ExamStatus = "ExamStatus"
    const val ExamId = "ExtraExamId"
    const val CertifyingStatement = "ExtraCertifyingStatement"
    const val Submitted = "ExtraSubmitted"
    const val UserId = "ExtraUserId"
    const val IsQuiz = "ExtraIsQuiz"
    const val UpdateQuizParam = "ExtraUpdateQuizParameter"
    const val IsPassed = "ExtraIsPassed"
    const val ExamType = "ExtraExamType"

    const val SearchTag = "ExtraSearchTag"
    const val StartGuide = "StartGuide"

    /** using for SearchActivity */
    const val AutoFocusing = "AutoFocusing"

    /** using for FriendsActivity */
    const val FriendType = "ExtraFriendType"
    const val ProfileNickName = "ProfileNickName"
    const val FollowChangedStatus = "FollowChangedFriend"
    const val FollowChangedUserId = "FollowChangedUserId"

    /** using for StartActivity */
    const val RequirementQuestion = "ExtraRequirementQuestion"
    const val RequirementPlaceholder = "ExtraRequirementPlaceholder"
    const val Timer = "ExtraTimer"
    const val RequirementAnswer = "ExtraRequirementAnswer"

    /** using for SearchActivity */
    const val CreateProblemType = "CreateProblemType"

    /** using for DynamicLink */
    const val DynamicLinkExamId = "DynamicLinkExamId"
}
