/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.user.model.User
import java.util.Date

@Immutable
data class Exam(
    val title: String,
    val description: String,
    val thumbnailUrl: String?,
    val certifyingStatement: String,
    val buttonTitle: String,
    val isPublic: Boolean,
    val tags: ImmutableList<Tag>,
    val user: User,
    val deletedAt: Date? = null,
    val id: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val solvedCount: Int,
    val answerRate: Float,
    val canRetry: Boolean,
)
