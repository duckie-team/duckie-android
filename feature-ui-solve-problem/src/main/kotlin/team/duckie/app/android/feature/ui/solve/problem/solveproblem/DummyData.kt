/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
@file:Suppress(
    "MaxLineLength",
    "MaxmumLineLength",
)

package team.duckie.app.android.feature.ui.solve.problem.solveproblem

import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.ChoiceModel
import team.duckie.app.android.domain.exam.model.ImageChoiceModel
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question

val dummyList = persistentListOf(
    Problem(
        id = 0,
        question = Question.Text(text = "신카이 마코토 명작"),
        answer = Answer.Short(),
        hint = null,
        memo = null,
        correctAnswer = "너의 이름은",
    ),
    Problem(
        id = 1,
        question = Question.Image(
            text = "해당 장소를 보고 떠오르는 영화",
            imageUrl = "https://images.unsplash.com/photo-1568822241089-05fe03f22b25?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80",
        ),
        answer = Answer.Choice(
            choices = persistentListOf(
                ChoiceModel(text = "재벌집 막내아들"),
                ChoiceModel(text = "신세계"),
                ChoiceModel(text = "너의 이름은"),
                ChoiceModel(text = "나루토 더 제네레이션"),
            ),
        ),
        hint = null,
        memo = null,
        correctAnswer = "3",
    ),
    Problem(
        id = 2,
        question = Question.Audio(
            text = "이 노래는?",
            audioUrl = "https://ccrma.stanford.edu/~jos/mp3/harpsi-cs.mp3",
        ),
        answer = Answer.Choice(
            choices = persistentListOf(
                ChoiceModel(text = "비발디 - 사계"),
                ChoiceModel(text = "베토벤 피아노 협주곡"),
                ChoiceModel(text = "바흐 하프시코드"),
            ),
        ),
        hint = null,
        memo = null,
        correctAnswer = "3",
    ),
    Problem(
        id = 3,
        question = Question.Video(
            text = "이 게임의 이름은?",
            videoUrl = "https://va.media.tumblr.com/tumblr_o600t8hzf51qcbnq0_480.mp4",
        ),
        answer = Answer.ImageChoice(
            imageChoice = persistentListOf(
                ImageChoiceModel(
                    text = "젤다",
                    imageUrl = "https://images.unsplash.com/photo-1566577134657-a8b2da3b4dcb?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80",
                ),
                ImageChoiceModel(
                    text = "너의 이름은",
                    imageUrl = "https://images.unsplash.com/photo-1568822241089-05fe03f22b25?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80",
                ),
                ImageChoiceModel(
                    text = "메이플스토리",
                    imageUrl = "https://images.unsplash.com/photo-1590846083693-f23fdede3a7e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
                ),
            ),
        ),
        hint = null,
        memo = null,
        correctAnswer = "3",
    ),
)
