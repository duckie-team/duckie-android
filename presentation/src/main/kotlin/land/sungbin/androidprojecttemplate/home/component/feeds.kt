package land.sungbin.androidprojecttemplate.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import land.sungbin.androidprojecttemplate.R
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackRowTag
import team.duckie.quackquack.ui.component.QuackSubtitle2

val dummyTags = persistentListOf(
    "디즈니", "픽사", "마블", "DC", "애니메이션", "지브리", "ost", "피규어"
)

@Composable
internal fun Header(
    @DrawableRes profile: Int,
    title: String,
    content: String,
    tagItems: PersistentList<String>,
    tagItemsSelection: List<Boolean>,
    onTagClick: (
        index: Int,
    ) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
        )
    ) {
        QuackImage(
            src = R.drawable.duckie_profile,
            overrideSize = DpSize(
                width = 36.dp,
                height = 36.dp
            )
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 4.dp,
                )
            ) {
                QuackSubtitle2(
                    text = title,
                )
                QuackBody1(
                    text = content,
                    singleLine = false,
                )
            }
            QuackRowTag(
                items = tagItems,
                itemsSelection = tagItemsSelection,
                onClick = onTagClick,
            )
        }

    }
}