/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.feature.profile.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.user.model.UserProfile.Companion.username
import team.duckie.app.android.feature.profile.R
import team.duckie.app.android.feature.profile.component.EmptyText
import team.duckie.app.android.feature.profile.screen.section.ExamSection
import team.duckie.app.android.feature.profile.screen.section.FavoriteTagSection
import team.duckie.app.android.feature.profile.screen.section.FollowSection
import team.duckie.app.android.feature.profile.viewmodel.ProfileViewModel
import team.duckie.app.android.feature.profile.viewmodel.state.mapper.toUiModel
import team.duckie.app.android.common.compose.ui.BackPressedHeadLineTopAppBar
import team.duckie.app.android.common.compose.ui.Create
import team.duckie.app.android.common.compose.ui.dialog.DuckieSelectableType
import team.duckie.app.android.common.compose.ui.dialog.IgnoreCheckDialog
import team.duckie.app.android.common.compose.ui.dialog.DuckieSelectableBottomSheetDialog
import team.duckie.app.android.common.compose.ui.dialog.ReportDialog
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun OtherProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val tags = remember(state.userProfile.user?.favoriteTags) {
        state.userProfile.user?.favoriteTags?.toImmutableList() ?: persistentListOf()
    }
    val submittedExams = remember(state.userProfile.createdExams) {
        state.userProfile.createdExams?.map(ProfileExam::toUiModel)?.toImmutableList()
            ?: persistentListOf()
    }

    ReportDialog(
        onClick = { viewModel.updateReportDialogVisible(false) },
        visible = state.reportDialogVisible,
        onDismissRequest = { viewModel.updateReportDialogVisible(false) },
    )

    IgnoreCheckDialog(
        visible = state.ignoreDialogVisible,
        targetName = state.userProfile.username(),
        onIgnoreRequest = {
            coroutineScope.launch {
                bottomSheetState.hide()
            }
            viewModel.ignore(state.userId)
            viewModel.updateIgnoreDialogVisible(false)
        },
        onDismissRequest = { viewModel.updateIgnoreDialogVisible(false) },
    )

    DuckieSelectableBottomSheetDialog(
        types = state.bottomSheetDialogType,
        bottomSheetState = bottomSheetState,
        closeSheet = {
            coroutineScope.launch { bottomSheetState.hide() }
        },
        onReport = viewModel::report,
        onIgnore = {
            viewModel.updateIgnoreDialogVisible(true)
        },
    ) {
        ProfileScreen(
            modifier = modifier,
            userProfile = state.userProfile,
            isLoading = state.isLoading,
            editSection = {
                FollowSection(
                    enabled = state.follow,
                    onClick = viewModel::clickFollow,
                )
            },
            topBar = {
                BackPressedHeadLineTopAppBar(
                    title = state.userProfile.username(),
                    isLoading = state.isLoading,
                    onBackPressed = viewModel::clickBackPress,
                    trailingIcon = QuackIcon.More,
                    onTrailingIconClick = {
                        viewModel.updateBottomSheetDialogType(DuckieSelectableType.Ignore)
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                )
            },
            tagSection = {
                FavoriteTagSection(
                    isLoading = state.isLoading,
                    title = stringResource(id = R.string.favorite_tag),
                    emptySection = {
                        EmptyText(message = stringResource(id = R.string.not_yet_add_favorite_tag))
                    },
                    tags = tags,
                    onClickTag = viewModel::onClickTag,
                )
            },
            submittedExamSection = {
                ExamSection(
                    isLoading = state.isLoading,
                    icon = QuackIcon.Create,
                    title = stringResource(id = R.string.submitted_exam),
                    exams = submittedExams,
                    onClickExam = viewModel::clickExam,
                    onClickMore = null,
                    emptySection = {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            EmptyText(message = stringResource(id = R.string.not_yet_submit_exam))
                        }
                    },
                )
            },
            onClickExam = viewModel::clickExam,
            onClickMore = {
                viewModel.updateBottomSheetDialogType(type = DuckieSelectableType.Report)
                coroutineScope.launch {
                    bottomSheetState.show()
                }
            },
            onClickFriend = viewModel::navigateFriends,
        )
    }
}
