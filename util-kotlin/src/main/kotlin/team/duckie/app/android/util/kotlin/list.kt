/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.kotlin

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * To efficiently import a list of other players with a list of players and updated elements,
 * one simple solution is to copy the list to a changeable list, update the desired element,
 * and use Kotlin's "Read-Only Access" list interface to save only references to the list of results.
 */
inline fun <T> List<T>.copy(mutator: MutableList<T>.() -> Unit): List<T> {
    return toMutableList().apply(mutator)
}

/**
 * [ImmutableList] 의 팩토리 함수입니다
 *
 * @param size 리스트의 크기
 * @param init 초기화 빌더
 *
 * @return [ImmutableList]를 반환합니다.
 */
inline fun <T> ImmutableList(
    size: Int,
    init: (Int) -> T
): ImmutableList<T> {
    return List(size, init).toImmutableList()
}
