package land.sungbin.androidprojecttemplate.common


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.component.QuackMenuFab
import team.duckie.quackquack.ui.component.QuackMenuFabItem


@Composable
internal fun DuckieFab(
    items: PersistentList<QuackMenuFabItem>,
    expanded: Boolean,
    onFabClick: () -> Unit,
    onItemClick: (index: Int, item: QuackMenuFabItem) -> Unit,
    paddingProvider: () -> PaddingValues,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                paddingProvider()
            ),
        contentAlignment = Alignment.BottomEnd,
    ) {
        QuackMenuFab(
            items = items,
            expanded = expanded,
            onFabClick = onFabClick,
            onItemClick = onItemClick,
        )
    }
}