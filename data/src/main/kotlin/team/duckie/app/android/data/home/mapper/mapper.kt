/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.home.mapper

import HomeFundingsResponseData
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.data.heart.mapper.toDomain
import team.duckie.app.android.data.home.model.HomeFundingData
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.domain.home.model.HomeFunding
import team.duckie.app.android.domain.home.model.HomeFundingsResponse

internal fun HomeFundingData.toDomain() = HomeFunding(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    title = title ?: duckieResponseFieldNpe("${this::class.java.simpleName}.title"),
    thumbnailUrl = thumbnailUrl
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.thumbnailUrl"),
    totalProblemCount = totalProblemCount
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.totalProblemCount"),
    problemCount = problemCount
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.problemCount"),
    contributorCount = contributorCount
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.contributorCount"),
    user = user?.toDomain(),
    heart = heart?.toDomain(),
)

internal fun HomeFundingsResponseData.toDomain() = HomeFundingsResponse(
    upcomingExams = upcomingExams?.map { it.toDomain() }
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.upcomingExams")
)
