/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.recommendation.mapper

import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.android.data.category.mapper.toDomain
import team.duckie.app.android.data.exam.mapper.toDomain
import team.duckie.app.android.data.recommendation.model.RecommendationJumbotronItemData
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.domain.recommendation.model.RecommendationJumbotronItem
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.recommendation.model.RecommendationData
import team.duckie.app.android.data.recommendation.model.RecommendationItemData
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.domain.recommendation.model.ExamType
import team.duckie.app.android.domain.recommendation.model.RecommendationFeeds
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.common.kotlin.fastMap

internal fun RecommendationData.toDomain() = RecommendationFeeds(
    jumbotrons = jumbotrons?.fastMap(ExamData::toDomain)?.toPersistentList(),
    recommendations = recommendations?.fastMap(RecommendationItemData::toDomain)?.toPersistentList()
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.recommendations"),
    page = page,
)

internal fun RecommendationJumbotronItemData.toDomain() = RecommendationJumbotronItem(
    answerRate = answerRate ?: duckieResponseFieldNpe("${this::class.java.simpleName}.answerRate"),
    buttonTitle = buttonTitle
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.buttonTitle"),
    category = category?.toDomain()
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.category"),
    certifyingStatement = certifyingStatement
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.certifyingStatement"),
    description = description
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.description"),
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    mainTag = mainTag?.toDomain()
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.mainTag"),
    solvedCount = solvedCount
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.solvedCount"),
    subTags = subTags?.fastMap(TagData::toDomain)
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.subTags"),
    thumbnailUrl = thumbnailUrl
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.thumbnailUrl"),
    title = title ?: duckieResponseFieldNpe("${this::class.java.simpleName}.title"),
    type = type?.let { ExamType.toExamType(it) }
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.type"),
)

internal fun RecommendationItemData.toDomain() = RecommendationItem(
    id = id,
    title = title ?: duckieResponseFieldNpe("${this::class.java.simpleName}.title"),
    tag = tag?.toDomain(),
    exams = exams?.fastMap(ExamData::toDomain)
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.items"),
)
