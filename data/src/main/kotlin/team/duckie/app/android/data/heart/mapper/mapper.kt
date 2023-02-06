/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.heart.mapper

import team.duckie.app.android.data.heart.model.HeartsData
import team.duckie.app.android.domain.heart.model.Hearts
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe

internal fun HeartsData.toDomain() = Hearts(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
)
