package land.sungbin.androidprojecttemplate.ui.component

import androidx.compose.runtime.Composable
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
fun BackArrowTopAppBar(
    text: String? = null,
    onClick: () -> Unit,
) {
    QuackTopAppBar(
        leadingIcon = QuackIcon.ArrowBack,
        onClickLeadingIcon = onClick,
        headline = text,
    )
}