/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.challengecomment.mapper

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.common.kotlin.exception.duckieSimpleResponseFieldNpe
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.data._util.toDate
import team.duckie.app.android.data.challengecomment.model.ChallengeCommentResponse
import team.duckie.app.android.data.challengecomment.model.CommentOrderTypeData
import team.duckie.app.android.data.challengecomment.model.GetChallengeCommentListResponse
import team.duckie.app.android.data.heart.mapper.toDomain
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.domain.challengecomment.model.ChallengeComment
import team.duckie.app.android.domain.challengecomment.model.ChallengeCommentList
import team.duckie.app.android.domain.challengecomment.model.CommentOrderType

internal fun GetChallengeCommentListResponse.toDomain() = ChallengeCommentList(
    total = total ?: duckieSimpleResponseFieldNpe<GetChallengeCommentListResponse>("total"),
    data = data?.fastMap(ChallengeCommentResponse::toDomain)?.toImmutableList()
        ?: duckieSimpleResponseFieldNpe<GetChallengeCommentListResponse>("data"),
)

internal fun ChallengeCommentResponse.toDomain() = ChallengeComment(
    id = id ?: duckieSimpleResponseFieldNpe<ChallengeCommentResponse>("id"),
    message = message ?: duckieSimpleResponseFieldNpe<ChallengeCommentResponse>("message"),
    wrongAnswer = wrongAnswer
        ?: "", // TODO(limsaehyun): 테스트 단계에서만 nullable하게 처리할 것
    heartCount = heartCount ?: duckieSimpleResponseFieldNpe<ChallengeCommentResponse>("heartCount"),
    createdAt = createdAt?.toDate() ?: duckieSimpleResponseFieldNpe<ChallengeCommentResponse>("createdAt"),
    user = user?.toDomain() ?: duckieSimpleResponseFieldNpe<ChallengeCommentResponse>("user"),
    heart = heart?.toDomain(),
)

internal fun CommentOrderType.toData() = when (this) {
    CommentOrderType.DATE -> CommentOrderTypeData.DATE
    CommentOrderType.LIKE -> CommentOrderTypeData.LIKE
}
