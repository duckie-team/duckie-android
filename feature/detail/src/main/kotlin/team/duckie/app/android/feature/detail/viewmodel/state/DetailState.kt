/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail.viewmodel.state

import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.examInstance.model.ExamStatus
import team.duckie.app.android.domain.recommendation.model.ExamType
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.detail.viewmodel.DetailViewModel

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
        val reportDialogVisible: Boolean = false,
        val isRefreshing: Boolean = false,
        val examStatus: ExamStatus = ExamStatus.Ready,
    ) : DetailState() {
        private val tags: List<Tag>
            get() = mutableListOf<Tag>().apply {
                exam.mainTag?.run { add(this) }
                exam.subTags?.run { addAll(this) }
            }

        val tagNames: List<String>
            get() = tags.fastMap { it.name }

        val mainTagNames: String
            get() = tags.firstOrNull()?.name ?: ""

        val profileImageUrl: String
            get() = exam.user?.profileImageUrl ?: ""

        val nickname: String
            get() = exam.user?.nickname ?: ""

        val buttonTitle: String
            get() = exam.buttonTitle ?: ""

        val certifyingStatement: String
            get() = exam.certifyingStatement ?: ""

        val isOwner: Boolean
            get() = exam.user?.id == appUser.id

        val isHeart: Boolean
            get() = exam.heart?.id != null

        val isQuiz: Boolean
            get() = ExamType.toExamType(exam.type ?: "") == ExamType.Challenge
    }

    /**
     * [DetailViewModel] 의 비즈니스 로직 처리중에 예외가 발생한 상태를 나타냅니다.
     *
     * @param exception 발생한 예외
     */
    class Error(
        val exception: Throwable,
        val isNetworkError: Boolean = false,
    ) : DetailState()
}
