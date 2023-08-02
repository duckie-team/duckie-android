/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack.todo.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.SnapSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable


/**
 * 꽥꽥에서 사용할 [AnimationSpec] 에 대한 정보
 */
object QuackAnimationSpec {
    /**
     * 일부 환경에서는 애니메이션이 없이 진행돼야 할 때도 있습니다.
     * 이 값을 true 로 설정하면 모든 애니메이션을 무시합니다.
     *
     * **이 값의 변경은 모든 애니메이션에 영향을 미치므로 신중하게 사용해야 합니다.**
     */
    var snapMode: Boolean = false

    /**
     * 꽥꽥에서 사용할 [애니메이션의 기본 스팩][AnimationSpec]
     *
     * @return 덕키에서 사용할 [AnimationSpec]. [snapMode] 에 따라 반환값이 달라집니다.
     * false 라면 덕키에서 사용하는 애니메이션 스팩인 [TweenSpec] 이 반환되고,
     * true 라면 [SnapSpec] 이 반환됩니다.
     *
     * @see snapMode
     */
    operator fun <T> invoke(): DurationBasedAnimationSpec<T> = when (snapMode) {
        true -> snap()
        else -> tween(
            durationMillis = 250,
            easing = LinearEasing,
        )
    }
}

/**
 * [QuackAnimationSpec.snapMode] 대신에 한 번만 선택적으로 애니메이션 여부를
 * 결정하기 위해 사용할 수 있습니다.
 *
 * @param useAnimation 애니메이션을 사용할지 여부
 *
 * @return [useAnimation] 여부에 따른 [DurationBasedAnimationSpec]
 */
@Suppress("FunctionName")
@Stable
public fun <T> QuackOptionalAnimationSpec(
    useAnimation: Boolean,
): DurationBasedAnimationSpec<T> = when (useAnimation) {
    true -> QuackAnimationSpec()
    else -> snap()
}
