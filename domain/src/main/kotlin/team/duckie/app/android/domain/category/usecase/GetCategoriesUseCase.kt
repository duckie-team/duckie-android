/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.category.usecase

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.category.repository.CategoryRepository

@Immutable
class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository,
) {
    suspend operator fun invoke(withPopularTags: Boolean) = runCatching {
        repository.getCategories(withPopularTags).toImmutableList()
    }
}
