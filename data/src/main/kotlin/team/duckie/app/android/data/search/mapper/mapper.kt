/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.search.mapper

import team.duckie.app.android.data.exam.mapper.toDomain
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.search.model.SearchData
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.search.model.Search
import team.duckie.app.android.util.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap

internal fun SearchData.toDomain() = when (this.type) {
    SearchType.Exams.type -> Search.ExamSearch(
        exams = result?.exams?.fastMap(ExamData::toDomain)
            ?: duckieResponseFieldNpe("${this::class.java.simpleName}.result.exams"),
    )

    SearchType.Users.type -> Search.UserSearch(
        users = result?.users?.fastMap(UserResponse::toDomain)
            ?: duckieResponseFieldNpe("${this::class.java.simpleName}.result.users"),
    )

    SearchType.Tags.type -> Search.TagSearch(
        tags = result?.tags?.fastMap(TagData::toDomain)
            ?: duckieResponseFieldNpe("${this::class.java.simpleName}.result.tags"),
    )

    else -> duckieResponseFieldNpe("${this::class.java.simpleName}.result.${this.type}")
}
