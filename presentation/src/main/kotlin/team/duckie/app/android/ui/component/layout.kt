package team.duckie.app.android.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackHeadLine1

private val SimpleLayoutBottomPadding = PaddingValues(
    start = 16.dp,
    end = 16.dp,
)
private val SimpleLayoutContentPadding = PaddingValues(
    horizontal = 20.dp,
)
private val titleAndDescriptionSpace = 4.dp

/**
 * TODO : NestedScroll 을 좀 더 스맛하게 처리할 수 있을까
 */
private val DP_MAX = 1000.dp


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
                    .heightIn(min = 0.dp, max = DP_MAX)
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
