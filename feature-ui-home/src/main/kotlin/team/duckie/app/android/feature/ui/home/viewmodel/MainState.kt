/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.ui.home.constants.BottomNavigationStep

internal data class MainState(
    val bottomNavigationStep: BottomNavigationStep = BottomNavigationStep.HomeScreen,

    val popularTags: ImmutableList<Tag> = persistentListOf(),

    val guideVisible: Boolean = false,
)
