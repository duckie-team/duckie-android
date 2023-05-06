/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.tag.edit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.tag.usecase.TagCreateUseCase
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import javax.inject.Inject

@HiltViewModel
internal class TagEditViewModel @Inject constructor(
    private val getMeUseCase: GetMeUseCase,
    private val tagCreateUseCase: TagCreateUseCase,
) : ContainerHost<TagEditState, TagEditSideEffect>, ViewModel() {

    override val container = container<TagEditState, TagEditSideEffect>(TagEditState.Loading)

    private var me: User = User.empty()
    private var myTags: ImmutableList<Tag> = persistentListOf()

    fun initState() {
        intent {
            getMeUseCase()
                .onSuccess { meResponse ->
                    me = meResponse
                    myTags = me.favoriteTags?.toImmutableList() ?: persistentListOf()

                    reduce {
                        TagEditState.Success(
                            myTags = me.favoriteTags?.toImmutableList() ?: persistentListOf(),
                        )
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
        intent {
            reduce { TagEditState.AddTag() }
        }
    }

    /** 각 태그 항목의 x 버튼 클릭 시 동작 */
    fun onTrailingClick() {
    }

    /** 태그 편집 화면에서, 각 태그 항목 클릭 시 동작 */
    fun onTagClick(index: Int) {
    }

    /** 태그 추가 화면 종료 시 동작 */
    fun onAddFinishClick() {
        intent { reduce { TagEditState.Success() } }
    }

    /** 태그 추가 화면에서, 검색된 항목 클릭 시 동작 */
    fun onSearchTagClick(index: Int) = viewModelScope.launch {
    }

    /** 태그 추가 화면에서, 텍스트 내용 변경 시 동작 */
    fun onSearchTextChanged(newSearchTextValue: String) {
    }

    /** 태그 추가 화면에서, 텍스트 유효성 체크 */
    fun onSearchTextValidate(searchTextValue: String): Boolean {
        return true
    }

    private suspend fun addMyTag(newTag: Tag) {
        myTags = myTags.toMutableList().apply { add(newTag) }.toPersistentList()
    }
}
