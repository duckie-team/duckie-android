package land.sungbin.androidprojecttemplate.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackHeadLine1

private val SimpleLayoutBottomPadding = PaddingValues(
    start = 16.dp,
    end = 16.dp,
    bottom = 16.dp,
)
private val SimpleLayoutContentPadding = PaddingValues(
    horizontal = 20.dp,
)
private val titleAndDescriptionSpace = 4.dp


@Composable
fun TitleAndDescription(
    title: String,
    description: String,
) {
    Column {
        QuackHeadLine1(text = title, singleLine = false)
        Spacer(modifier = Modifier.height(titleAndDescriptionSpace))
        QuackBody1(text = description)
    }
}

@Composable
fun DuckieSimpleLayout(
    topAppBar: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    bottomContent: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            topAppBar()

            Column(
                modifier = Modifier.padding(SimpleLayoutContentPadding)
            ) {
                content()
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    SimpleLayoutBottomPadding
                )
        ) {
            bottomContent()
        }
    }
}