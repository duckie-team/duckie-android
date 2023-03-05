/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.model

/**
 * 검색할 때 사용되는 타입 enum [SearchType]
 *
 * [USERS] 사용자
 * [EXAMS] 덕질고사
 * [TAGS] 태그
 */
enum class SearchType {
    USERS,
    EXAMS,
    TAGS,
}
