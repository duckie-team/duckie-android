/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.profile.R
import team.duckie.app.android.feature.ui.profile.screen.section.EditButton
import team.duckie.app.android.shared.ui.compose.Spacer
import team.duckie.quackquack.ui.color.QuackColor

@Composable
internal fun EditSection(
    onClickEditProfile: () -> Unit,
    onClickEditTag: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        EditButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.edit_profile),
            onClick = onClickEditProfile,
            plainTextColor = QuackColor.Black,
        )
        Spacer(space = 8.dp)
        EditButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.edit_tag),
            onClick = onClickEditTag,
            plainTextColor = QuackColor.Black,
        )
    }
}
