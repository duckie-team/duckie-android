/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(OutOfDateApi::class)

package team.duckie.app.android.feature.ui.detail.viewmodel.state
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.ui.detail.viewmodel.DetailViewModel
import team.duckie.app.android.util.kotlin.OutOfDateApi

/**
 * [DetailViewModel] 의 상태를 나타냅니다.
 */
sealed class DetailState {
    /** [DetailViewModel] 의 로딩 중인 상태를 나타냅니다. */
    object Loading : DetailState()

    /** [DetailViewModel] 데이터를 잘 받았을 때의 상태를 나타냅니다. */
    data class Success(
        val exam: Exam,
        val appUser: User,
        val isFollowing: Boolean = false,
    ) : DetailState() {
        val isOwner: Boolean
            get() = exam.user.id == appUser.id

        val isHeart: Boolean
            get() = exam.heart?.id != null
    }

    /**
     * [DetailViewModel] 의 비즈니스 로직 처리중에 예외가 발생한 상태를 나타냅니다.
     *
     * @param exception 발생한 예외
     */
    class Error(val exception: Throwable) : DetailState()
}
