/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.kotlin

/* "#" 문자를 String 앞에 추가 합니다 */
fun String.addHashTag() = buildString {
    insert(0, "#")
}

fun String.addAmountString() = buildString {
    append("개")
}

fun String.addCountString() = buildString {
    append("번")
}

/* String 연산을 위한 builder  */
fun String.buildString(builder: StringBuilder.() -> Unit) =
    StringBuilder(this).apply(builder).toString()
