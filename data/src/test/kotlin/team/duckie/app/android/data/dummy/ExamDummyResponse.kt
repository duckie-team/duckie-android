/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.dummy

import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.DuckPower
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserStatus

object ExamDummyResponse {
    const val RawData = """
        {
            "id": 1,
            "title": "도로 덕질고사",
            "description": "안녕하세요 개발자분들 도로 덕질고사 한 번 풀어보시죠",
            "thumbnailUrl": "",
            "thumbnailType": "text",
            "buttonTitle": "도로 덕질하러 가기",
            "certifyingStatement": "Love Doro",
            "solvedCount": 1,
            "answerRate": 0,
            "mainTag": {
                "name": "doro",
                "id": 1
            },
            "subTags": [
                {
                    "name": "블랙핑크",
                    "id": 2
                }
            ],
            "category": {
                "id": 1,
                "name": "연예인",
                "thumbnailUrl": "https://duckie"
            },
            "type": "text",
            "user": {
                "id": 1,
                "nickName": "doro",
                "profileImageUrl": "",
                "status": "NEW",
                "duckPower" : {
                    "id": 1,
                    "tier": "덕력 20%",
                    "tag": {
                        "id": 1,
                        "name": "doro"
                    }
                }
            },
            "status": "PENDING",
            "heart": null,
            "heartCount": 3,
            "challenges": [],
            "perfectScoreImageUrl": null
        }
    """

    val DomainData = Exam(
        id = 1,
        title = "도로 덕질고사",
        description = "안녕하세요 개발자분들 도로 덕질고사 한 번 풀어보시죠",
        thumbnailUrl = "",
        thumbnailType = "text",
        type = "text",
        buttonTitle = "도로 덕질하러 가기",
        certifyingStatement = "Love Doro",
        solvedCount = 1,
        answerRate = 0f,
        user = User(
            id = 1,
            nickname = "doro",
            profileImageUrl = "",
            status = UserStatus.NEW,
            duckPower = DuckPower(
                id = 1,
                tier = "덕력 20%",
                tag = Tag(
                    id = 1,
                    name = "doro",
                ),
            ),
            follow = null,
            favoriteTags = null,
            favoriteCategories = null,
            permissions = null,
            introduction = null,
        ),
        category = Category(1, "연예인", "https://duckie", null),
        mainTag = Tag(1, "doro"),
        subTags = persistentListOf(
            Tag(2, "블랙핑크"),
        ),
        status = "PENDING",
        heart = null,
        heartCount = 3,
        quizs = persistentListOf(),
        perfectScoreImageUrl = null,
    )
}
