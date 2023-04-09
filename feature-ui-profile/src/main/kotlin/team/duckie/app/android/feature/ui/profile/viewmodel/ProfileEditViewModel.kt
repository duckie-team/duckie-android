/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.feature.ui.profile.viewmodel.state.ProfileEditState
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val loadGalleryImagesUseCase: LoadGalleryImagesUseCase,
) : ViewModel(), ContainerHost<ProfileEditState, Unit> {
    override val container: Container<ProfileEditState, Unit> = container(ProfileEditState())


    private var mutableGalleryImages = persistentListOf<String>()
    val galleryImages: ImmutableList<String> get() = mutableGalleryImages

    private fun addGalleryImages(images: List<String>) = intent {
        mutableGalleryImages = persistentListOf(*images.toTypedArray())
    }

    fun changeProfile(profile: Any?) = intent {
        loadGalleryImagesUseCase().onSuccess {
            addGalleryImages(it)
        }
        reduce {
            state.copy(profile = profile)
        }
    }

    fun changeNickName(nickName: String) = intent {
        reduce {
            state.copy(nickName = nickName)
        }
    }
}
