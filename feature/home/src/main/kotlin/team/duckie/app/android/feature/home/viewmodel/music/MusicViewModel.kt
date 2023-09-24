/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.viewmodel.music

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
internal class MusicViewModel @Inject constructor(

) : ContainerHost<MusicState, MusicSideEffect>, ViewModel() {
    override val container = container<MusicState, MusicSideEffect>(MusicState())

    fun saveHeroModulePage(page: Int) = intent {
        reduce {
            state.copy(heroModulePage = page)
        }
    }

    fun clickRetryMusic() = intent {
        postSideEffect(MusicSideEffect.ListPullUp)
    }

    fun clickMusicExam(examId: Int) = intent {
        postSideEffect(MusicSideEffect.NavigateToMusicExamDetail(examId))
    }
}

