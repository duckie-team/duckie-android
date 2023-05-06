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
                    reduce { TagEditState.Error(it) }
                    postSideEffect(TagEditSideEffect.ReportError(it))
                }
        }
    }

    /** 수정완료 버튼 누를 시 동작 */
    fun onEditFinishClick() {
        intent { postSideEffect(TagEditSideEffect.FinishTagEdit(true)) }
    }

    /** + 직접 태그 추가하기 버튼 클릭 시 동작 */
    fun onAddTagClick() {
        intent { postSideEffect(TagEditSideEffect.AddTagEdit) }
    }

    /** 각 태그 항목의 x 버튼 클릭 시 동작 */
    fun onTrailingClick() {
    }

    /** 각 태그 항목 클릭 시 동작 */
    fun onTagClick(index: Int) {
    }
}
