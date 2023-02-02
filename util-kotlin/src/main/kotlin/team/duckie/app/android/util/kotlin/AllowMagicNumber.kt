/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.kotlin

/**
 * MagicNumber 을 예외적으로 허용할 때 사용하는 어노테이션 입니다.
 *
 * @param because MagicNumber 을 허용하는 이유
 */
@Target(
    AnnotationTarget.EXPRESSION,
    AnnotationTarget.CLASS,
    AnnotationTarget.FILE,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.FUNCTION,
)
@Retention(AnnotationRetention.SOURCE)
annotation class AllowMagicNumber(val because: String = "")
