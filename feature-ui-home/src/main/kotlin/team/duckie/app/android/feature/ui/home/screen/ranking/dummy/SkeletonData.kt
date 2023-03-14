/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.ranking.dummy

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.DuckPower
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.util.kotlin.randomString

internal val skeletonExamineeItems = (1..10).map {
    if (it == 1) {
        User.empty().copy(
            id = it,
            profileImageUrl = ThumbnailExample,
            nickname = randomString(6),
            duckPower = DuckPower(id = it, tier = "99", Tag(id = it, name = randomString(4))),
            favoriteTags = listOf(Tag(id = it, name = "도로패션")),
        )
    } else {
        User.empty().copy(
            id = it,
            profileImageUrl = ThumbnailExample,
            nickname = randomString(3),
            duckPower = DuckPower(id = it, tier = "99", Tag(id = it, name = randomString(4))),
        )
    }
}.toImmutableList()

internal val skeletonRankingExams = (1..10).map {
    Exam.empty().copy(
        id = it,
        user = User.empty().copy(nickname = randomString(3)),
        solvedCount = 20,
        description = randomString(6),
    )
}.toImmutableList()

internal val skeletonTags = persistentListOf(
    Tag(id = 0, name = "전체"),
    Tag(id = 1, name = randomString(3)),
    Tag(id = 2, name = randomString(3)),
    Tag(id = 3, name = randomString(3)),
)

private const val ThumbnailExample =
    "https://plus.unsplash.com/premium_photo-1669737010860-fc8de46eeb39?ixlib" +
            "=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1974&q=80"
