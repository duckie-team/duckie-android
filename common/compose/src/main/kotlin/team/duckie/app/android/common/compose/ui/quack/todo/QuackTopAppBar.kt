/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.icon.v1.TextLogoId
import team.duckie.app.android.common.compose.ui.icon.v1.toQuackV1Icon
import team.duckie.app.android.common.kotlin.runtimeCheck
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon as QuackV1Icon

/**
 * 덕키의 Top Navigation Bar 를 그립니다.
 * [QuackTopAppBar] 는 몇몇 중요한 특징이 있습니다.
 *
 * - 최소 하나의 값이 제공돼야 합니다.
 * - 항상 상위 컴포저블의 가로 길이에 꽉차게 그립니다.
 * - [showLogoAtCenter] 이 true 라면 [centerText] 값은 무시됩니다.
 *   로고 리소스로는 [QuackIcon.TextLogoId] 를 사용합니다.
 * - [centerText] 값이 있다면 [showLogoAtCenter] 값은 무시됩니다.
 *   또한 [centerText] 는 trailing content 로 아이콘을 배치할 수 있습니다.
 * - [trailingExtraIconResId] 과 [trailingIconResId] 이 하나라도 들어왔다면 [trailingText] 는 무시되며,
 *   `[trailingExtraIconResId] [trailingIconResId]` 순서로 배치됩니다.
 * - [trailingText] 값이 입력되면 [trailingExtraIconResId] 과 [trailingIconResId] 값은 무시됩니다.
 * - [trailingContent] 이 있다면 [trailingIconResId], [trailingExtraIconResId], [trailingText],
 *   [onTrailingIconClick], [onTrailingExtraIconClick], [onTrailingTextClick] 값은 무시됩니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param leadingIconResId leading content 로 배치할 아이콘 resId
 * @param leadingText leading content 로 배치할 텍스트. 선택적으로 값을 받습니다.
 * @param onLeadingIconClick [leadingIconResId] 이 클릭됐을 때 실행될 람다
 * @param showLogoAtCenter center content 로 덕키의 로고를 배치할지 여부
 * @param centerText center content 에 로고 대신에 표시할 텍스트
 * @param centerTextTrailingIconResId [centerText] 의 trailing content 로 배치할 아이콘 resId
 * @param onCenterClick center content 가 클릭됐을 때 실행될 람다
 * @param trailingContent trailing content 로 배치할 컴포넌트
 * @param trailingIconResId trailing content 로 배치할 아이콘
 * @param trailingExtraIconResId trailing content 에 추가로 배치할 아이콘
 * @param trailingText trailing content 에 배치할 텍스트
 * @param onTrailingIconClick [trailingIconResId] 이 클릭됐을 때 실행될 람다
 * @param onTrailingExtraIconClick [trailingExtraIconResId] 이 클릭됐을 때 실행될 람다
 * @param onTrailingTextClick [trailingText] 가 클릭됐을 때 실행될 람다
 */
@Composable
fun QuackTopAppBar(
    modifier: Modifier = Modifier,
    leadingIconResId: Int? = null,
    leadingText: String? = null,
    onLeadingIconClick: (() -> Unit)? = null,
    showLogoAtCenter: Boolean? = null,
    centerText: String? = null,
    centerTextTrailingIconResId: Int? = null,
    onCenterClick: (() -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    trailingIconResId: Int? = null,
    trailingExtraIconResId: Int? = null,
    trailingText: String? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    onTrailingExtraIconClick: (() -> Unit)? = null,
    onTrailingTextClick: (() -> Unit)? = null,
) {
    val leadingIcon = leadingIconResId?.toQuackV1Icon
    val centerTextTrailingIcon = centerTextTrailingIconResId?.toQuackV1Icon
    val trailingIcon = trailingIconResId?.toQuackV1Icon
    val trailingExtraIcon = trailingExtraIconResId?.toQuackV1Icon

    runtimeCheck(
        leadingIcon != null ||
                leadingText != null ||
                onLeadingIconClick != null ||
                showLogoAtCenter != null ||
                centerText != null ||
                centerTextTrailingIcon != null ||
                onCenterClick != null ||
                trailingContent != null ||
                trailingIcon != null ||
                trailingExtraIcon != null ||
                trailingText != null ||
                onTrailingIconClick != null ||
                onTrailingExtraIconClick != null ||
                onTrailingTextClick != null,
    ) {
        "At least one param setting is required."
    }

    if (trailingContent != null) {
        runtimeCheck(
            trailingIcon == null && trailingExtraIcon == null && trailingText == null &&
                    onTrailingIconClick == null && onTrailingExtraIconClick == null &&
                    onTrailingTextClick == null,
        ) {
            "trailingContent 가 입력되었을 때는 다른 trailing content 인자들을 이용하실 수 없습니다."
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = QuackTopAppBarDefaults.BackgroundColor.value),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        QuackTopAppBarDefaults.LeadingContent(
            icon = leadingIcon,
            text = leadingText,
            onIconClick = onLeadingIconClick,
        )
        QuackTopAppBarDefaults.CenterContent(
            showLogo = showLogoAtCenter,
            text = centerText,
            textTrailingIcon = centerTextTrailingIcon,
            onClick = onCenterClick,
        )
        // https://github.com/duckie-team/quack-quack-android/issues/412
        // TrailingContent content 가 없어도 width 를 차지함
        if (trailingContent != null) {
            trailingContent()
        } else {
            QuackTopAppBarDefaults.TrailingContent(
                icon = trailingIcon,
                extraIcon = trailingExtraIcon,
                text = trailingText,
                onIconClick = onTrailingIconClick,
                onExtraIconClick = onTrailingExtraIconClick,
                onTextClick = onTrailingTextClick,
            )
        }
    }
}

/**
 * QuackTopAppBar 를 그리기 위한 리소스들을 정의합니다.
 */
private object QuackTopAppBarDefaults {
    val BackgroundColor = QuackColor.White

    private val CenterTypography = QuackTypography.Body1
    private val TrailingTypography = QuackTypography.Subtitle.change(
        color = QuackColor.Gray2,
    )

    private val TrailingTextPadding = PaddingValues(
        horizontal = 16.dp,
        vertical = 15.dp,
    )

    private val LogoIcon: QuackV1Icon = QuackIcon.TextLogoId.toQuackV1Icon!!
    private val LogoIconSize = DpSize(
        width = 72.dp,
        height = 24.dp,
    )
    private val LogoPadding = PaddingValues(
        vertical = 12.dp,
    )

    private val CenterTextPadding = PaddingValues(
        vertical = 15.dp,
    )
    private val CenterIconTint = QuackColor.Gray1

    /**
     * 모든 영역에서 사용되는 공통 아이콘 사이즈
     */
    private val IconSize = DpSize(
        width = 24.dp,
        height = 24.dp,
    )
    private val IconPadding = PaddingValues(
        all = 8.dp,
    )

    /**
     * ```
     * [Icon][Icon]
     * ```
     *
     * 위와 같은 형식에서 적용되는 개별 아이콘들의 패딩
     *
     * 아이콘 사이 간격인 8 을, 둘로 나누어 배분하였습니다.
     * [IconSize] 는 아이콘이 하나만 쓰일 때 적용됩니다.
     */
    private val ExtraIconPadding = PaddingValues(
        all = 4.dp,
    )

    /**
     * trailing content 에 배치될 아이콘의 패딩을 조건에 맞게 계산합니다.
     *
     * @param hasExtraIcon extra icon 을 가지고 있는지 여부
     *
     * @return trailing content 에 배치될 아이콘의 패딩
     */
    @Stable
    private fun trailingIconPaddingFor(
        hasExtraIcon: Boolean,
    ) = when (hasExtraIcon) {
        true -> ExtraIconPadding
        false -> IconPadding
    }

    /**
     * leading content 를 배치합니다.
     *
     * @param icon 배치할 아이콘
     * @param text 배치할 텍스트. 선택적으로 값을 받습니다.
     * @param onIconClick [icon] 이 클릭됐을 때 실행될 람다
     */
    @Composable
    fun LeadingContent(
        icon: QuackV1Icon?,
        text: String? = null,
        onIconClick: (() -> Unit)? = null,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackImage(
                modifier = Modifier
                    .size(IconSize)
                    .padding(IconPadding)
                    .quackClickable(
                        rippleEnabled = false,
                        onClick = onIconClick,
                    ),
                src = icon,
            )
            text?.let {
                // TODO: 최대 width 처리
                // 아마 커스텀 레이아웃 필요할 듯
                QuackText(
                    text = text,
                    typography = QuackTypography.HeadLine2,
                    singleLine = true,
                )
            }
        }
    }

    /**
     * center content 를 배치합니다.
     *
     * - [showLogo] 이 true 라면 [text] 값은 무시됩니다.
     *   로고 리소스로는 [QuackIcon.TextLogoId] 를 사용합니다.
     * - [text] 값이 있다면 [showLogo] 값은 무시됩니다.
     *   또한 [text] 는 trailing icon 을 가질 수 있습니다.
     *
     * @param showLogo 덕키의 로고를 배치할지 여부
     * @param text 로고 대신에 표시할 텍스트
     * @param textTrailingIcon [text] 의 trailing content 로 표시될 아이콘
     * @param onClick center content 가 클릭됐을 때 실행될 람다
     */
    @Composable
    fun CenterContent(
        showLogo: Boolean? = null,
        text: String? = null,
        textTrailingIcon: team.duckie.quackquack.ui.icon.QuackIcon? = null,
        onClick: (() -> Unit)? = null,
    ) {
        if (showLogo == true) {
            runtimeCheck(
                value = text == null,
            ) {
                "로고와 텍스트를 동시에 표시할 수 없습니다"
            }
        }
        Row(
            modifier = Modifier.quackClickable(
                rippleEnabled = false,
                onClick = onClick,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showLogo == true) {
                QuackImage(
                    modifier = Modifier
                        .size(LogoIconSize)
                        .padding(LogoPadding),
                    src = LogoIcon,
                )
            } else {
                text?.let {
                    QuackText(
                        modifier = Modifier.padding(
                            paddingValues = CenterTextPadding,
                        ),
                        text = text,
                        typography = CenterTypography,
                        singleLine = true,
                    )
                    QuackImage(
                        modifier = Modifier.size(IconSize),
                        src = textTrailingIcon,
                        tint = CenterIconTint,
                    )
                }
            }
        }
    }

    /**
     * trailing content 를 그립니다.
     *
     * - [extraIcon] 과 [icon] 이 하나라도 들어왔다면 [text] 는 무시되며,
     *   `[extraIcon] [icon]` 순서로 배치됩니다.
     * - [text] 값이 입력되면 [extraIcon] 과 [icon] 값은 무시됩니다.
     *
     * @param icon 배치할 아이콘
     * @param extraIcon 추가로 배치할 아이콘
     * @param text 배치할 텍스트
     * @param onIconClick [icon] 이 클릭됐을 때 실행될 람다
     * @param onExtraIconClick [extraIcon] 이 클릭됐을 때 실행될 람다
     * @param onTextClick [text] 가 클릭됐을 때 실행될 람다
     */
    @Composable
    fun TrailingContent(
        icon: QuackV1Icon? = null,
        extraIcon: QuackV1Icon? = null,
        text: String? = null,
        onIconClick: (() -> Unit)? = null,
        onExtraIconClick: (() -> Unit)? = null,
        onTextClick: (() -> Unit)? = null,
    ) {
        if (icon != null || extraIcon != null) {
            runtimeCheck(
                value = text == null,
            ) {
                "아이콘과 텍스트를 동시에 표시할 수 없습니다"
            }
        }
        if (text != null) {
            runtimeCheck(
                value = icon == null && extraIcon == null,
            ) {
                "텍스트와 아이콘을 동시에 표시할 수 없습니다"
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            text?.let {
                QuackText(
                    modifier = Modifier
                        .quackClickable(
                            rippleEnabled = false,
                            onClick = onTextClick,
                        )
                        .padding(
                            paddingValues = TrailingTextPadding,
                        ),
                    text = text,
                    typography = TrailingTypography,
                    singleLine = true,
                )
            }
            extraIcon?.let {
                QuackImage(
                    src = extraIcon,
                    modifier = Modifier
                        .size(IconSize)
                        .padding(trailingIconPaddingFor(hasExtraIcon = true))
                        .quackClickable(
                            rippleEnabled = false,
                            onClick = onExtraIconClick,
                        ),
                )
            }
            icon?.let {
                QuackImage(
                    src = icon,
                    modifier = Modifier
                        .size(IconSize)
                        .padding(trailingIconPaddingFor(hasExtraIcon = extraIcon != null))
                        .quackClickable(
                            rippleEnabled = false,
                            onClick = onIconClick,
                        ),
                )
            }
        }
    }
}
