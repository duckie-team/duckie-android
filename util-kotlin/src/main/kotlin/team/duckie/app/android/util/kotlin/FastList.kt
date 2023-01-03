/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

// TAKEN FROM: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/ui/ui-util/src/commonMain/kotlin/androidx/compose/ui/util/ListUtils.kt

package team.duckie.app.android.util.kotlin

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Iterates through a [List] using the index and calls [action] for each item.
 * This does not allocate an iterator like [Iterable.forEach].
 *
 * **random access 의 개념을 이해하고 사용해야 합니다!**
 * 그렇지 않으면 성능이 오히려 떨어지는 부작용이 있을 수 있습니다.
 */
@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T> List<T>.fastForEach(action: (T) -> Unit) {
    contract { callsInPlace(action) }
    for (index in indices) {
        val item = get(index)
        action(item)
    }
}

/**
 * Iterates through a [List] using the index and calls [action] for each item.
 * This does not allocate an iterator like [Iterable.forEachIndexed].
 *
 * **random access 의 개념을 이해하고 사용해야 합니다!**
 * 그렇지 않으면 성능이 오히려 떨어지는 부작용이 있을 수 있습니다.
 */
@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T> List<T>.fastForEachIndexed(action: (Int, T) -> Unit) {
    contract { callsInPlace(action) }
    for (index in indices) {
        val item = get(index)
        action(index, item)
    }
}

/**
 * Returns `true` if all elements match the given [predicate].
 *
 * **random access 의 개념을 이해하고 사용해야 합니다!**
 * 그렇지 않으면 성능이 오히려 떨어지는 부작용이 있을 수 있습니다.
 */
@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T> List<T>.fastAll(predicate: (T) -> Boolean): Boolean {
    contract { callsInPlace(predicate) }
    fastForEach { if (!predicate(it)) return false }
    return true
}

/**
 * Returns `true` if at least one element matches the given [predicate].
 *
 * **random access 의 개념을 이해하고 사용해야 합니다!**
 * 그렇지 않으면 성능이 오히려 떨어지는 부작용이 있을 수 있습니다.
 */
@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T> List<T>.fastAny(predicate: (T) -> Boolean): Boolean {
    contract { callsInPlace(predicate) }
    fastForEach { if (predicate(it)) return true }
    return false
}

/**
 * Returns the first value that [predicate] returns `true` for or `null` if nothing matches.
 *
 * **random access 의 개념을 이해하고 사용해야 합니다!**
 * 그렇지 않으면 성능이 오히려 떨어지는 부작용이 있을 수 있습니다.
 */
@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T> List<T>.fastFirstOrNull(predicate: (T) -> Boolean): T? {
    contract { callsInPlace(predicate) }
    fastForEach { if (predicate(it)) return it }
    return null
}

/**
 * Returns the sum of all values produced by [selector] function applied to each element in the
 * list.
 *
 * **random access 의 개념을 이해하고 사용해야 합니다!**
 * 그렇지 않으면 성능이 오히려 떨어지는 부작용이 있을 수 있습니다.
 */
@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T> List<T>.fastSumBy(selector: (T) -> Int): Int {
    contract { callsInPlace(selector) }
    var sum = 0
    fastForEach { element ->
        sum += selector(element)
    }
    return sum
}

/**
 * `List<T>.flatten` 의 random access 버전을 구현합니다.
 *
 * @param transform flatten 할 List 를 변형하는 람다
 */
@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T> List<List<T>>.fastFlatten(transform: (List<T>) -> List<T> = { it }): List<T> {
    contract { callsInPlace(transform) }
    return buildList {
        this@fastFlatten.fastForEach { values ->
            addAll(transform(values))
        }
    }
}

/**
 * Returns a list containing the results of applying the given [transform] function
 * to each element in the original collection.
 *
 * **random access 의 개념을 이해하고 사용해야 합니다!**
 * 그렇지 않으면 성능이 오히려 떨어지는 부작용이 있을 수 있습니다.
 */
@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T, R> List<T>.fastMap(transform: (T) -> R): List<R> {
    contract { callsInPlace(transform) }
    val target = ArrayList<R>(size)
    fastForEach {
        target += transform(it)
    }
    return target
}

/**
 * Returns a list containing the results of applying the given [transform] function
 * to each element in the original collection.
 *
 * **random access 의 개념을 이해하고 사용해야 합니다!**
 * 그렇지 않으면 성능이 오히려 떨어지는 부작용이 있을 수 있습니다.
 */
@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T, R> List<T>.fastMapIndexed(transform: (Int, T) -> R): List<R> {
    contract { callsInPlace(transform) }
    val target = ArrayList<R>(size)
    fastForEachIndexed { index, item ->
        target += transform(index, item)
    }
    return target
}

// TODO: should be fastMaxByOrNull to match stdlib
/**
 * Returns the first element yielding the largest value of the given function or `null` if there
 * are no elements.
 *
 * **random access 의 개념을 이해하고 사용해야 합니다!**
 * 그렇지 않으면 성능이 오히려 떨어지는 부작용이 있을 수 있습니다.
 */
@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T, R : Comparable<R>> List<T>.fastMaxBy(selector: (T) -> R): T? {
    contract { callsInPlace(selector) }
    if (isEmpty()) return null
    var maxElem = get(0)
    var maxValue = selector(maxElem)
    for (i in 1..lastIndex) {
        val e = get(i)
        val v = selector(e)
        if (maxValue < v) {
            maxElem = e
            maxValue = v
        }
    }
    return maxElem
}

/**
 * Applies the given [transform] function to each element of the original collection
 * and appends the results to the given [destination].
 *
 * **random access 의 개념을 이해하고 사용해야 합니다!**
 * 그렇지 않으면 성능이 오히려 떨어지는 부작용이 있을 수 있습니다.
 */
@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T, R, C : MutableCollection<in R>> List<T>.fastMapTo(
    destination: C,
    transform: (T) -> R
): C {
    contract { callsInPlace(transform) }
    fastForEach { item ->
        destination.add(transform(item))
    }
    return destination
}
