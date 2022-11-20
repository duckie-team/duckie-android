package land.sungbin.androidprojecttemplate.ui.main.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.shared.compose.extension.noRippleClickable
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackRoundImage
import team.duckie.quackquack.ui.component.QuackTitle1
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
fun DrawerContent(
    onClickSetting: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 20.dp,
        )
    ) {
        DrawerHeader()
        QuackDivider()
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
            )
        ) {
            QuackTitle2(
                text = stringResource(id = R.string.trading_activity)
            )
            DrawerIconText(
                icon = QuackIcon.Heart,
                text = stringResource(id = R.string.list_of_interests),
                onClick = {

                }
            )
            DrawerIconText(
                icon = QuackIcon.Sell,
                text = stringResource(id = R.string.sales_history),
                onClick = {

                }
            )
            DrawerIconText(
                icon = QuackIcon.Buy,
                text = stringResource(id = R.string.purchase_history),
                onClick = {

                }
            )
        }
        QuackDivider()
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
            )
        ) {
            QuackTitle2(
                text = stringResource(id = R.string.my_activity)
            )
            DrawerIconText(
                icon = QuackIcon.Area,
                text = stringResource(id = R.string.setting_up_areas_interest),
                onClick = {

                }
            )
            DrawerIconText(
                icon = QuackIcon.Tag,
                text = stringResource(id = R.string.edit_interest_tags),
                onClick = {

                }
            )
        }
        QuackDivider()
        DrawerIconText(
            modifier = Modifier.padding(
                start = 16.dp,
            ),
            icon = QuackIcon.Setting,
            text = stringResource(id = R.string.app_settings),
            onClick = onClickSetting,
        )
    }
}

@Composable
private fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
            )
            .padding(
                horizontal = 16.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {

                }
                .padding(
                    vertical = 4.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                )
            ) {
                QuackRoundImage(
                    src = R.drawable.duckie_profile,
                    size = DpSize(
                        width = 48.dp,
                        height = 48.dp,
                    )
                )
                QuackHeadLine2(
                    text = "닉네임"
                )
            }
            QuackImage(
                src = QuackIcon.ArrowRight,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 42.dp,
            )
        ) {
            DrawerNumberText(number = "2.6만", text = stringResource(id = R.string.follower))
            DrawerNumberText(number = "167", text = stringResource(id = R.string.following))
            DrawerNumberText(number = "88", text = stringResource(id = R.string.feed))
        }
    }

}

@Composable
private fun DrawerNumberText(
    number: String,
    text: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            2.dp,
        )
    ) {
        QuackTitle2(
            text = number
        )
        QuackBody2(
            text = text,
        )
    }
}

@NonRestartableComposable
@Composable
private fun DrawerIconText(
    modifier: Modifier = Modifier,
    icon: QuackIcon,
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .noRippleClickable(
                onClick = onClick,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
        )
    ) {
        QuackImage(
            src = icon,
        )
        QuackTitle1(
            text = text
        )
    }
}