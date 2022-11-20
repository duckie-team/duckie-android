package team.duckie.app.android.ui.main.setting.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackTitle2


private val SimpleLayoutContentPadding = PaddingValues(
    horizontal = 16.dp,
)
private val DpMax = 1000.dp

@Composable
internal fun BaseAppSettingLayout(
    topAppBar: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            topAppBar()

            Column(
                modifier = Modifier
                    .padding(SimpleLayoutContentPadding)
                    .verticalScroll(scrollState)
                    .heightIn(min = 0.dp, max = DpMax)
            ) {
                content()
            }
        }
    }
}

@Stable
internal val AppSettingTextPadding = PaddingValues(
    vertical = 12.dp,
)

@Composable
internal fun PaddingQuackTitle2(
    text: String,
    onClick: (() -> Unit)? = null,
) {
    QuackTitle2(
        modifier = Modifier
            .padding(AppSettingTextPadding),
        text = text,
        color = QuackColor.Black,
        onClick = onClick,
    )
}

@Composable
internal fun PaddingQuackBody1(
    text: String,
    onClick: (() -> Unit)? = null,
) {
    QuackBody1(
        modifier = Modifier
            .padding(AppSettingTextPadding),
        text = text,
        color = QuackColor.Black,
        onClick = onClick,
    )
}

@Composable
internal fun PaddingTitleAndContentText(
    text: String,
    content: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .padding(AppSettingTextPadding)
            .clickable {
                onClick?.let { it() }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackBody1(
            text = text,
        )

        Spacer(modifier = Modifier.width(8.dp))

        QuackBody2(
            text = content,
            color = QuackColor.Gray2,
        )
    }
}

