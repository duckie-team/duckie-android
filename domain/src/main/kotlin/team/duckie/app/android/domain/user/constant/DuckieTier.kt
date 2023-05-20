/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package team.duckie.app.android.domain.user.constant

import androidx.compose.runtime.Immutable
import team.duckie.app.android.common.kotlin.AllowMagicNumber

@AllowMagicNumber
@Immutable
enum class DuckieTier(val level: Int) {
    DuckKid(0),

    // TODO(riflockle7): 백앤드에 1000 이 BRONZE 가 맞는지 확인 필요
    DuckBronze(1000),
}
