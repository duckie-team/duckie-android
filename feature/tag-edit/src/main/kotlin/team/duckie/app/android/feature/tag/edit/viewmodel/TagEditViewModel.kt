/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.tag.edit.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.kotlin.copy
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.tag.usecase.TagCreateUseCase
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.domain.user.usecase.SetMeUseCase
import team.duckie.app.android.domain.user.usecase.UserUpdateUseCase
import javax.inject.Inject

@HiltViewModel
internal class TagEditViewModel @Inject constructor(
    private val getMeUseCase: GetMeUseCase,
    private val setMeUseCase: SetMeUseCase,
    private val tagCreateUseCase: TagCreateUseCase,
    private val userUpdateUseCase: UserUpdateUseCase,
) : ContainerHost<TagEditState, TagEditSideEffect>, ViewModel() {

    override val container = container<TagEditState, TagEditSideEffect>(TagEditState.Loading)

    private var me: User = User.empty()
    private val myTags: ImmutableList<Tag>
        get() = me.favoriteTags?.toImmutableList() ?: persistentListOf()

    fun initState() = intent {
        getMeUseCase()
            .onSuccess { meResponse ->
                me = meResponse
                reduce { TagEditState.Success(myTags) }
            }.onFailure {
                reduce { TagEditState.Error(it) }
                postSideEffect(TagEditSideEffect.ReportError(it))
            }
    }

    /** 수정완료 버튼 누를 시 동작 */
    fun onEditFinishClick() = intent { updateMe() }

    /** 각 태그 항목의 x 버튼 클릭 시 동작 */
    fun onTrailingClick(index: Int) = intent {
        require(state is TagEditState.Success)
        val newTags = (state as TagEditState.Success).myTags.copy { removeAt(index) }
        reduce { TagEditState.Success(myTags = newTags) }
    }

    /** 바텀 시트에서 오른쪽 방향 화살표를 눌러 태그 추가를 완료한다. */
    fun addNewTags(newTag: List<Tag>) {
        val newTags = myTags.copy { addAll(newTag) }
        intent { reduce { TagEditState.Success(myTags = newTags) } }
    }

    /** 새로운 태그를 요청한다. */
    suspend fun requestNewTag(tag: String): Tag? {
        require(container.stateFlow.value is TagEditState.Success)
        return tagCreateUseCase(tag).getOrNull()
    }

    /** 태그 추가 화면에서, 태그 추가한 새로운 me 정보를 갱신하고 [TagEditViewModel] 내 정보 또한 갱신한다. */
    private suspend fun updateMe() {
        val tagSuccessState = container.stateFlow.value
        require(tagSuccessState is TagEditState.Success)

        userUpdateUseCase(
            id = me.id,
            profileImageUrl = null,
            categories = null,
            tags = tagSuccessState.myTags,
            status = null,
            nickname = null,
            introduction = null,
        ).onSuccess { newMe ->
            setMeUseCase(newMe)
            me = newMe
            intent { postSideEffect(TagEditSideEffect.FinishTagEdit(true)) }
        }.onFailure {
            intent { postSideEffect(TagEditSideEffect.ReportError(it)) }
        }
    }
}
