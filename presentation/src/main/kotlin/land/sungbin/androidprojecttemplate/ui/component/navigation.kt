package land.sungbin.androidprojecttemplate.ui.component

import androidx.compose.runtime.Composable
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
fun BackArrowTopAppBar(
    onClick: () -> Unit
){
    QuackTopAppBar(
        leadingIcon = QuackIcon.ArrowBack,
        onClickLeadingIcon = onClick,
    )
}