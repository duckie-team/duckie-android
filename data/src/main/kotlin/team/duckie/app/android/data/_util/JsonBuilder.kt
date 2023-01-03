/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data._util

import team.duckie.app.android.util.kotlin.DuckieDsl
import team.duckie.app.android.util.kotlin.fastForEach

@DuckieDsl
internal interface JsonBuilder {
    infix fun String.withInt(value: Int)
    infix fun String.withFloat(value: Float)
    infix fun String.withBoolean(value: Boolean)
    infix fun String.withString(value: String)
    infix fun String.withPojo(value: Any)

    infix fun String.withInts(value: List<Int>)
    infix fun String.withFloats(value: List<Float>)
    infix fun String.withBooleans(value: List<Boolean>)
    infix fun String.withStrings(value: List<String>)
    infix fun String.withPojos(value: List<Any>)

    fun build(): String
}

// 매 DSL 마다 `builder` 인스턴스를 새로 만들어야 함
// -> 싱글톤 불가
internal class JsonBuilderInstance : JsonBuilder {
    private val builder = jsonMapper.createObjectNode()

    override infix fun String.withInt(value: Int) {
        builder.put(this, value)
    }

    override infix fun String.withFloat(value: Float) {
        builder.put(this, value)
    }

    override infix fun String.withBoolean(value: Boolean) {
        builder.put(this, value)
    }

    override infix fun String.withString(value: String) {
        builder.put(this, value)
    }

    override infix fun String.withPojo(value: Any) {
        builder.putPOJO(this, value)
    }

    override fun String.withInts(value: List<Int>) {
        builder.putArray(this).also { array ->
            value.fastForEach { array.add(it) }
        }
    }

    override fun String.withFloats(value: List<Float>) {
        builder.putArray(this).also { array ->
            value.fastForEach { array.add(it) }
        }
    }

    override fun String.withBooleans(value: List<Boolean>) {
        builder.putArray(this).also { array ->
            value.fastForEach { array.add(it) }
        }
    }

    override fun String.withStrings(value: List<String>) {
        builder.putArray(this).also { array ->
            value.fastForEach { array.add(it) }
        }
    }

    override fun String.withPojos(value: List<Any>) {
        builder.putArray(this).also { array ->
            value.fastForEach { array.addPOJO(it) }
        }
    }

    override fun build(): String {
        return builder.toString()
    }
}

internal inline fun buildJson(builder: JsonBuilder.() -> Unit): String {
    return with(JsonBuilderInstance()) {
        builder()
        build()
    }
}
