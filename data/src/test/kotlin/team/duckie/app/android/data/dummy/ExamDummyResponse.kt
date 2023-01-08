/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(OutOfDateApi::class)

package team.duckie.app.android.data.dummy

import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.ChoiceModel
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.util.kotlin.OutOfDateApi

@OutOfDateApi
object ExamDummyResponse {
    const val RawData = """
        {
            "id": 1,
            "title": "도로 덕질고사",
            "description": "안녕하세요 개발자분들 도로 덕질고사 한 번 풀어보시죠",
            "thumbnailUrl": null,
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
            "problems": [
                {
                    "id": 1,
                    "answer": {
                        "type": "choice",
                        "choices": [
                            {
                                "text": "교촌치킨"
                            },
                            {
                                "text": "BBQ"
                            },
                            {
                                "text": "지코바"
                            },
                            {
                                "text": "엄마가 만들어준 치킨"
                            }
                        ]
                    },
                    "question": {
                        "type": "text",
                        "text": "다음 중 도로가 가장 좋아하는 치킨 브랜드는?"
                    },
                    "hint": "",
                    "memo": "",
                    "correctAnswer": "지코바"
                }
            ],
            "type": "text"
        }
    """

    val DomainData = Exam(
        id = 1,
        title = "도로 덕질고사",
        description = "안녕하세요 개발자분들 도로 덕질고사 한 번 풀어보시죠",
        thumbnailUrl = null,
        buttonTitle = "도로 덕질하러 가기",
        certifyingStatement = "Love Doro",
        solvedCount = 1,
        answerRate = 0f,
        category = Category(1, "연예인", "https://duckie", null),
        mainTag = Tag(1, "doro"),
        subTags = persistentListOf(
            Tag(2, "블랙핑크"),
        ),
        problems = persistentListOf(
            Problem(
                id = 1,
                answer = Answer.Choice(
                    persistentListOf(
                        ChoiceModel("교촌치킨"),
                        ChoiceModel("BBQ"),
                        ChoiceModel("지코바"),
                        ChoiceModel("엄마가 만들어준 치킨")
                    )
                ),
                question = Question.Text("다음 중 도로가 가장 좋아하는 치킨 브랜드는?"),
                hint = "",
                memo = "",
                correctAnswer = "지코바"
            ),
        ),
        type = "text"
    )
}
