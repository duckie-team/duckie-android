package land.sungbin.androidprojecttemplate.shared.domain.unit

import android.content.res.Resources

private val displayMetrics by lazy { Resources.getSystem().displayMetrics }

val Int.domainDp
    get() = Dp(this * displayMetrics.density)

val Int.domainSp
    get() = Sp(this * displayMetrics.scaledDensity)

private val Double.domainSp
    get() = this * displayMetrics.scaledDensity

val Int.domainPx
    get() = Px(domainDp.value * displayMetrics.density)

val Double.domainEm
    get() = Em((this.domainSp * 0.05).toFloat())
