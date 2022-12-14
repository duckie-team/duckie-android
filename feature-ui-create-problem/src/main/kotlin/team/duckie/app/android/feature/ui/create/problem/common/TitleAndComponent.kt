/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.component.QuackHeadLine2

@Suppress("FunctionName")
internal fun LazyListScope.TitleAndComponent(
    modifier: Modifier = Modifier,
    @StringRes stringResource: Int,
    component: @Composable () -> Unit,
) {
    this.item {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            QuackHeadLine2(
                text = stringResource(id = stringResource)
            )
            component()
        }
    }
}
