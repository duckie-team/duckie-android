/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.gallery.usecase

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.gallery.repository.GalleryRepository

class LoadGalleryImagesUseCase(
    private val repository: GalleryRepository,
) {
    operator fun invoke() = runCatching {
        repository.loadImages().toImmutableList()
    }
}
