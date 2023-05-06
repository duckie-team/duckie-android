/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.tag.edit.viewmodel

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.tag.model.Tag


sealed class TagEditState {
    object Loading : TagEditState()

    class Success(
        val myTags: ImmutableList<Tag> = persistentListOf(),
        val categoryTagList: ImmutableList<ImmutableList<Tag>> = persistentListOf(),
    ) : TagEditState()

    class AddTag(
        val searchResults: ImmutableList<Tag> = persistentListOf(),
    ) : TagEditState()

    data class Error(val exception: Throwable) : TagEditState()
}
