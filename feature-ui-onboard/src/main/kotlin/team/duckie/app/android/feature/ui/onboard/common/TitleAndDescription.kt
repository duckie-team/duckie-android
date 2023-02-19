/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackHeadLine1

@Composable
internal fun TitleAndDescription(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        QuackHeadLine1(text = stringResource(titleRes))
        QuackBody1(text = stringResource(descriptionRes))
    }
}
