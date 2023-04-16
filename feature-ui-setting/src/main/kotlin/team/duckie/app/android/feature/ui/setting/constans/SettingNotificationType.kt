/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.setting.constans

import androidx.annotation.StringRes
import team.duckie.app.android.feature.setting.R

/**
 * 설정에서 필요한 알림 값들을 정의합니다.
 *
 * [ExamReview] 덕력고사 검토
 * [ExamLike] 덕력고사 좋아요
 * [Follow] 팔로우
 * [Following] 팔로우한 유저
 * [FavoriteCategoryTag] 관심 카테고리 및 태그
 * [Ranking] 명예의 전당
 * [Notice] 공지사항
 */
enum class SettingNotificationType(
    @StringRes
    val title: Int,
    @StringRes
    val description: Int,
) {
    ExamReview(
        title = R.string.setting_notification_exam_review,
        description = R.string.setting_notification_exam_review_description,
    ),
    ExamLike(
        title = R.string.setting_notification_exam_like,
        description = R.string.setting_notification_exam_like_description,
    ),
    Follow(
        title = R.string.setting_notification_follow,
        description = R.string.setting_notification_follow_description,
    ),
    Following(
        title = R.string.setting_notification_following,
        description = R.string.setting_notification_following_description,
    ),
    FavoriteCategoryTag(
        title = R.string.setting_notification_favorite_category_tag,
        description = R.string.setting_notification_favorite_category_tag_description,
    ),
    Ranking(
        title = R.string.setting_notification_ranking,
        description = R.string.setting_notification_ranking_description,
    ),
    Notice(
        title = R.string.setting_notification_notice,
        description = R.string.setting_notification_notice_description,
    ),
}
