/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.dummy

import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.search.model.Search

object SearchDummyResponse {
    const val RawData = ""

    val DomainData = Search.TagSearch(
        tags = persistentListOf(),
    )
}
