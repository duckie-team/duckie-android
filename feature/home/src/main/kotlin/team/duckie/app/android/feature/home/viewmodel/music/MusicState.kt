/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.viewmodel.music

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.exam.model.Exam

data class MusicState(
    val heroModulePage: Int = 0,
    val musicJumbotrons: ImmutableList<Exam> = persistentListOf(
        Exam.empty()
    ),
)
