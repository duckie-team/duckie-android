/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.constans

import androidx.annotation.StringRes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.android.feature.setting.R

internal enum class Withdraweason(
    @StringRes
    val description: Int? = null,
) {
    INITIAL,

    /** 원하는 콘텐츠가 별로 없어요. */
    LACK_OF_CONTENT(
        description = R.string.withdraw_lack_of_content,
    ),

    /** 자주 사용하지 않는 앱이에요. */
    INFREQUENT_USE(
        description = R.string.withdraw_infrequent_use,
    ),

    /** 잦은 오류가 발생해서 쓸 수가 없어요. */
    FREQUENT_ERRORS(
        description = R.string.withdraw_frequent_errors,
    ),

    /** 새 계정으로 가입하려구요. */
    NEW_ACCOUNT_REGISTRATION(
        description = R.string.withdraw_new_account_registration,
    ),

    /** 기타 */
    OTHERS(
        description = R.string.others,
    ),
    ;

    companion object {
        fun getSignOutReason(): ImmutableList<Withdraweason> {
            return Withdraweason.values()
                .toList()
                .filterNot { it == INITIAL }
                .toPersistentList()
        }
    }
}
