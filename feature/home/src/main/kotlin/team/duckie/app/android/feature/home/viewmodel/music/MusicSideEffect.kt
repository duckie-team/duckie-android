/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.viewmodel.music

sealed class MusicSideEffect {
    object ListPullUp : MusicSideEffect()
    class NavigateToMusicExamDetail(val examId: Int) : MusicSideEffect()
}
