/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.heart.mapper

import team.duckie.app.android.data.heart.model.HeartData
import team.duckie.app.android.domain.heart.model.Heart
import team.duckie.app.android.util.kotlin.exception.duckieResponseFieldNpe

internal fun HeartData.toDomain() = Heart(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
)
