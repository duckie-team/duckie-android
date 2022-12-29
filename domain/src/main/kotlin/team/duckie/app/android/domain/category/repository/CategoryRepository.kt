/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.category.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.category.model.Category

@Immutable
interface CategoryRepository {
    suspend fun getCategories(withPopularTags: Boolean): List<Category>
}
