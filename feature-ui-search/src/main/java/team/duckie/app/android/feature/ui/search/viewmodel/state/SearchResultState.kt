/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.viewmodel.state

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.search.constans.SearchResultStep
import team.duckie.app.android.shared.ui.compose.DuckTestCoverItem

data class SearchResultState(
    val isSearchResultLoading: Boolean = false,

    val searchTag: String = "",
    val tagSelectedTab: SearchResultStep = SearchResultStep.DUCK_TEST,

    val searchResultForTest: PersistentList<DuckTestCoverItem> = persistentListOf(),
    val searchResultForUser: PersistentList<User> = persistentListOf(),
) {
    data class User(
        val userId: Int,
        val profile: String,
        val name: String,
        val examineeNumber: Int,
        val createAt: String,
        val isFollowing: Boolean = false,
    )
}
