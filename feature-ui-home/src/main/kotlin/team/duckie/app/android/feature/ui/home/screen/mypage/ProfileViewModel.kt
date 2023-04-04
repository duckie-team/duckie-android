/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.user.usecase.FetchUserProfileUseCase
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
) : ContainerHost<ProfileState, ProfileSideEffect>, ViewModel() {

    override val container = container<ProfileState, ProfileSideEffect>(ProfileState())

    fun getUserProfile(userId: Int) = viewModelScope.launch {
        fetchUserProfileUseCase(userId).onSuccess {

        }.onFailure {

        }
    }
}
