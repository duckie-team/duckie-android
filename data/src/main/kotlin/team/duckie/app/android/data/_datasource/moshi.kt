/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data._datasource

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import team.duckie.app.android.data.exam.model.AnswerData
import team.duckie.app.android.data.exam.model.QuestionData

internal val MoshiBuilder = Moshi.Builder()
    .add(questionDataFactory)
    .add(answerDataFactory)
    .addLast(KotlinJsonAdapterFactory())
    .build()

private val questionDataFactory
    get() = PolymorphicJsonAdapterFactory.of(QuestionData::class.java, "type")
        .withSubtype(QuestionData.Text::class.java, "text")
        .withSubtype(QuestionData.Image::class.java, "image")
        .withSubtype(QuestionData.Audio::class.java, "audio")
        .withSubtype(QuestionData.Video::class.java, "video")

private val answerDataFactory
    get() = PolymorphicJsonAdapterFactory.of(AnswerData::class.java, "type")
        .withSubtype(AnswerData.ShortAnswer::class.java, "shortAnswer")
        .withSubtype(AnswerData.Choice::class.java, "choice")
        .withSubtype(AnswerData.ImageChoice::class.java, "imageChoice")
