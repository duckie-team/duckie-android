/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.feature.ui.profile.screen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import team.duckie.app.android.feature.ui.profile.R
import team.duckie.app.android.shared.ui.compose.BackPressedHeadLineTopAppBar
import team.duckie.app.android.feature.ui.profile.component.EmptyText
import team.duckie.app.android.feature.ui.profile.screen.section.FavoriteTagSection
import team.duckie.app.android.feature.ui.profile.screen.section.FollowSection
import team.duckie.app.android.feature.ui.profile.viewmodel.ProfileViewModel
import team.duckie.app.android.shared.ui.compose.dialog.ReportBottomSheetDialog
import team.duckie.app.android.shared.ui.compose.dialog.ReportDialog

@Composable
internal fun OtherProfileScreen(
    viewModel: ProfileViewModel,
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val tags = remember(state.userProfile.user?.favoriteTags) {
        state.userProfile.user?.favoriteTags?.toImmutableList() ?: persistentListOf()
    }

    ReportDialog(
        onClick = { viewModel.updateReportDialogVisible(false) },
        visible = state.reportDialogVisible,
        onDismissRequest = { viewModel.updateReportDialogVisible(false) },
    )
    ReportBottomSheetDialog(
        bottomSheetState = bottomSheetState,
        closeSheet = {
            coroutineScope.launch { bottomSheetState.hide() }
        },
        onReport = viewModel::report,
    ) {
        ProfileScreen(
            userProfile = state.userProfile,
            isLoading = state.isLoading,
            editSection = {
                FollowSection(
                    enabled = state.follow,
                    onClick = viewModel::followUser,
                )
            },
            topBar = {
                BackPressedHeadLineTopAppBar(
                    title = state.userProfile.user?.nickname ?: "",
                    isLoading = state.isLoading,
                    onBackPressed = viewModel::clickBackPress,
                )
            },
            tagSection = {
                FavoriteTagSection(
                    title = stringResource(id = R.string.favorite_tag),
                    emptySection = {
                        EmptyText(message = stringResource(id = R.string.not_yet_add_favorite_tag))
                    },
                    tags = tags,
                    onClickTag = viewModel::onClickTag,
                )
            },
            submittedExamSection = {
                EmptyText(message = stringResource(id = R.string.not_yet_submit_exam))
            },
            onClickExam = viewModel::clickExam,
            onClickMore = {
                coroutineScope.launch { bottomSheetState.show() }
            },
        )
    }
}
