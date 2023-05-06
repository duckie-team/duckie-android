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
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.search.model.Search
import team.duckie.app.android.domain.search.usecase.GetSearchUseCase
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.tag.usecase.TagCreateUseCase
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.domain.user.usecase.SetMeUseCase
import team.duckie.app.android.domain.user.usecase.UserUpdateUseCase
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.ui.const.Debounce
import javax.inject.Inject

private const val TagsMaximumCount = 10

@HiltViewModel
internal class TagEditViewModel @Inject constructor(
    private val getMeUseCase: GetMeUseCase,
    private val setMeUseCase: SetMeUseCase,
    private val getSearchUseCase: GetSearchUseCase,
    private val tagCreateUseCase: TagCreateUseCase,
    private val userUpdateUseCase: UserUpdateUseCase,
) : ContainerHost<TagEditState, TagEditSideEffect>, ViewModel() {

    override val container = container<TagEditState, TagEditSideEffect>(TagEditState.Loading)

    private var me: User = User.empty()
    private val myTags: ImmutableList<Tag>
        get() = me.favoriteTags?.toImmutableList() ?: persistentListOf()

    /** tags 검색 flow. 실질 동작 로직은 apply 내에 명세되어 있다. */
    @OptIn(FlowPreview::class)
    private val _getSearchTagsFlow: MutableSharedFlow<String> = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    ).apply {
        viewModelScope.launch {
            this@apply.debounce(Debounce.SearchSecond).collectLatest { query ->
                intent {
                    getSearchUseCase(query = query, page = 1, type = SearchType.Tags)
                        .onSuccess {
                            val searchResults = (it as Search.TagSearch).tags
                                .take(TagsMaximumCount)
                                .toImmutableList()

                            reduce {
                                TagEditState.AddTag(searchResults = searchResults)
                            }
                        }
                        .onFailure {
                            postSideEffect(TagEditSideEffect.ReportError(it))
                        }
                }
            }
        }
    }

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
    fun onTrailingClick(index: Int) = viewModelScope.launch {
        val newTags = myTags.toMutableList().apply { removeAt(index) }.toPersistentList()
        updateMe(newTags)
    }

    /**
     * 태그 편집 화면에서, 각 태그 항목 클릭 시 동작
     * // TODO(riflockle7): 본래는 [onTrailingClick] 에 들어갈 내용이며 추후 개선 필요
     */
    fun onTagClick(index: Int) = viewModelScope.launch {
        val newTags = myTags.toMutableList().apply { removeAt(index) }.toPersistentList()
        updateMe(newTags)
    }

    /** 태그 추가 화면 종료 시 동작 */
    fun onAddFinishClick() {
        intent { reduce { TagEditState.Success(myTags) } }
    }

    /** 태그 추가 화면에서, 검색된 항목 클릭 시 동작 */
    fun onSearchTagHeaderClick(tag: String) = viewModelScope.launch {
        require(container.stateFlow.value is TagEditState.AddTag)
        tagCreateUseCase(tag)
            .onSuccess { tag -> addMyTag(tag) }
            .onFailure {
                intent { postSideEffect(TagEditSideEffect.ReportError(it)) }
            }
    }

    /** 태그 추가 화면에서, 검색된 항목 클릭 시 동작 */
    fun onSearchTagClick(index: Int) = viewModelScope.launch {
        require(container.stateFlow.value is TagEditState.AddTag)
        val searchResults = (container.stateFlow.value as TagEditState.AddTag).searchResults
        addMyTag(searchResults[index])
    }

    /** 태그 추가 화면에서, 텍스트 내용 변경 시 동작 */
    fun onSearchTextChanged(newSearchTextValue: String) = viewModelScope.launch {
        _getSearchTagsFlow.emit(newSearchTextValue)
    }

    /** 태그 추가 화면에서, 텍스트 유효성 체크 */
    fun onSearchTextValidate(searchTextValue: String): Boolean {
        val state = container.stateFlow.value
        require(state is TagEditState.AddTag)

        return searchTextValue.isNotEmpty() &&
                !state.searchResults.fastMap { it.name }.contains(searchTextValue)
    }

    /** 태그 추가 화면에서, 태그 추가한 뒤 화면을 종료한다. */
    private suspend fun addMyTag(newTag: Tag) {
        val newTags = myTags.toMutableList().apply { add(newTag) }.toPersistentList()
        updateMe(newTags)
    }

    /** 태그 추가 화면에서, 태그 추가한 새로운 me 정보를 갱신하고 [TagEditViewModel] 내 정보 또한 갱신한다. */
    private suspend fun updateMe(newTags: PersistentList<Tag>) {
        userUpdateUseCase(
            id = me.id,
            profileImageUrl = null,
            categories = null,
            tags = newTags,
            status = null,
            nickname = null,
            introduction = null,
        ).onSuccess { newMe ->
            setMeUseCase(newMe)
            me = newMe
            intent { reduce { TagEditState.Success(myTags) } }
        }.onFailure {
            intent { postSideEffect(TagEditSideEffect.ReportError(it)) }
        }
    }
}
