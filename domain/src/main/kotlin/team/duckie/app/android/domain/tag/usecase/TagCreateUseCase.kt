/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.tag.usecase

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import team.duckie.app.android.domain.tag.repository.TagRepository

@Immutable
class TagCreateUseCase @Inject constructor(
    private val repository: TagRepository,
) {
    suspend operator fun invoke(name: String): Result<Int> {
        return runCatching {
            repository.create(name).id
        }
    }
}
