/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.kotlin

/**
 * To efficiently import a list of other players with a list of players and updated elements,
 * one simple solution is to copy the list to a changeable list, update the desired element,
 * and use Kotlin's "Read-Only Access" list interface to save only references to the list of results.
 */
inline fun <T> List<T>.copy(mutatorBlock: MutableList<T>.() -> Unit): List<T> {
    return toMutableList().apply(mutatorBlock)
}
