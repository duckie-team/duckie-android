/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.setting.constans

import androidx.annotation.StringRes
import team.duckie.app.android.feature.setting.R

/**
 * 설정 페이지의 종류를 정의합니다.
 *
 * [Main] 설정 메인 페이지
 * [AccountInfo] 계정 정보 페이지
 * [Notification] 알림 설정 페이지
 * [Inquiry] 문의하기 페이지
 * [TermsAndPolicies] 이용약관 및 개인정보 처리방침 페이지
 * [Version] 버전 정보 페이지
 * */
enum class SettingType(
    @StringRes
    val titleRes: Int,
) {
    Main(
        titleRes = R.string.app_setting,
    ),
    AccountInfo(
        titleRes = R.string.account_info,
    ),
    Notification(
        titleRes = R.string.notification,
    ),
    Inquiry(
        titleRes = R.string.inquiry,
    ),
    TermsAndPolicies(
        titleRes = R.string.terms_and_policies,
    ),
    Version(
        titleRes = R.string.version_info,
    ),
}
