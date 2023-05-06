/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.tag.edit.viewmodel

sealed interface TagEditSideEffect {
    /**
     * [TagEditViewModel] 의 비즈니스 로직 처리 중에 발생한 예외를 [exception] 으로 받고
     * 해당 exception 을 [FirebaseCrashlytics] 에 제보합니다.
     *
     * @param exception 발생한 예외
     */
    class ReportError(val exception: Throwable) : TagEditSideEffect

    /**
     * 태그 수정을 마친 경우 방출하는 SideEffect
     *
     * @param needRefresh 새로고침 필요한 지 여부 (ex. 태그 수정 성공했을 때 새로고침 필요)
     */
    class FinishTagEdit(val needRefresh: Boolean) : TagEditSideEffect

    /**
     * 태그 추가 버튼 클릭 시 동작하는 SideEffect
     *
     * @param needRefresh 새로고침 필요한 지 여부 (ex. 태그 수정 성공했을 때 새로고침 필요)
     */
    object AddTagEdit : TagEditSideEffect
}
