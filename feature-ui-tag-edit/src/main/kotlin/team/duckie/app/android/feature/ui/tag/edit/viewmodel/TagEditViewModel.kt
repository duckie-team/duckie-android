/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.tag.edit.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import javax.inject.Inject

@HiltViewModel
internal class TagEditViewModel @Inject constructor(
    private val getMeUseCase: GetMeUseCase,
) : ContainerHost<TagEditState, TagEditSideEffect>, ViewModel() {

    override val container = container<TagEditState, TagEditSideEffect>(TagEditState.Loading)

    fun initState() {
        intent {
            getMeUseCase()
                .onSuccess { me ->
                    reduce {
                        TagEditState.Success(me = me)
                    }
                }.onFailure {
                    postSideEffect(TagEditSideEffect.ReportError(it))
                }
        }
    }

    fun onAddTagClick() {

    }

    fun onTagClick(tag: String) {

    }
}
