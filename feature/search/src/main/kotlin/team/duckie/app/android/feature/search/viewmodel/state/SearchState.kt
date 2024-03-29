/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.search.viewmodel.state

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.search.constants.SearchResultStep
import team.duckie.app.android.feature.search.constants.SearchStep

/**
 * [SearchViewModel] 의 상태를 나타냅니다.
 *
 * [isSearchLoading] - 검색 화면의 로딩 상태 유무
 * [searchStep] 검색 화면의 단계
 * [recentSearch] 최근 검색어
 * [recommendSearchs] 추천 검색어
 * [searchKeyword] 검색어
 * [tagSelectedTab] 검색 결과에서 선택된 탭
 */
@Immutable
internal data class SearchState(
    val me: User? = null,
    val isSearchLoading: Boolean = false,
    val isSearchProblemError: Boolean = false,
    val isSearchUserError: Boolean = false,
    val searchStep: SearchStep = SearchStep.Search,
    val recentSearch: ImmutableList<String> = persistentListOf(),
    val recommendSearchs: Flow<PagingData<Tag>> = flow { PagingData.empty<Tag>() },
    val tagSelectedTab: SearchResultStep = SearchResultStep.DuckExam,
    val targetExamId: Int = 0,
    val bottomSheetVisible: Boolean = false,
    val reportDialogVisible: Boolean = false,
    val searchAutoFocusing: Boolean = false,
) {
    data class SearchUser(
        val userId: Int,
        val profileImgUrl: String,
        val nickname: String,
        val favoriteTag: String,
        val tier: String,
        val isFollowing: Boolean,
    )
}

internal fun User.toUiModel() =
    SearchState.SearchUser(
        userId = id,
        nickname = nickname,
        profileImgUrl = profileImageUrl ?: "",
        isFollowing = follow != null,
        favoriteTag = duckPower?.tag?.name ?: "",
        tier = duckPower?.tier ?: "",
    )
