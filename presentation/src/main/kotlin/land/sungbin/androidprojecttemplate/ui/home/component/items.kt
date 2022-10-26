package land.sungbin.androidprojecttemplate.ui.home.component

import team.duckie.quackquack.ui.component.QuackBottomSheetItem
import team.duckie.quackquack.ui.component.QuackMenuFabItem
import team.duckie.quackquack.ui.icon.QuackIcon

internal val homeFabMenuItems = listOf(
    QuackMenuFabItem(
        icon = QuackIcon.Feed,
        text = "피드",
    ),
    QuackMenuFabItem(
        icon = QuackIcon.Buy,
        text = "덕딜",
    )
)

internal val filterBottomSheetItems = listOf(
    QuackBottomSheetItem(
        title = "피드, 덕딜 함께 보기",
        isImportant = true,
    ),
    QuackBottomSheetItem(
        title = "피드만 보기",
        isImportant = false,
    ),
    QuackBottomSheetItem(
        title = "덕딜만 보기",
        isImportant = false,
    )
)

internal val moreBottomSheetItems = listOf(
    QuackBottomSheetItem(
        title = "",
        isImportant = false,
    ),
    QuackBottomSheetItem(
        title = "",
        isImportant = false,
    ),
    QuackBottomSheetItem(
        title = "신고 하기",
        isImportant = false,
    )
)