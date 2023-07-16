/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.profile.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.common.compose.ui.BackPressedHeadLineTopAppBar
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.common.compose.ui.icon.v1.Create
import team.duckie.app.android.common.compose.ui.icon.v1.Notice
import team.duckie.app.android.common.kotlin.FriendsType
import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.user.model.UserProfile
import team.duckie.app.android.feature.profile.R
import team.duckie.app.android.feature.profile.component.EmptyText
import team.duckie.app.android.feature.profile.component.HeadLineTopAppBar
import team.duckie.app.android.feature.profile.screen.section.EditSection
import team.duckie.app.android.feature.profile.screen.section.ExamSection
import team.duckie.app.android.feature.profile.screen.section.FavoriteTagSection
import team.duckie.app.android.feature.profile.viewmodel.state.ExamType
import team.duckie.app.android.feature.profile.viewmodel.state.ProfileStep
import team.duckie.app.android.feature.profile.viewmodel.state.mapper.toUiModel
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.Create
import team.duckie.quackquack.material.icon.quackicon.outlined.Setting
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackButton
import team.duckie.quackquack.ui.QuackButtonStyle
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi
import team.duckie.quackquack.ui.icon.QuackIcon as QuackV1Icon

@Composable
fun MyProfileScreen(
    modifier: Modifier = Modifier,
    isAccessedProfile: Boolean = false,
    navigateBack: (() -> Unit)? = null,
    userProfile: UserProfile,
    isLoading: Boolean,
    onClickSetting: () -> Unit,
    onClickNotification: () -> Unit,
    onClickEditProfile: () -> Unit,
    onClickEditTag: () -> Unit,
    onClickExam: (DuckTestCoverItem) -> Unit,
    onClickMakeExam: () -> Unit,
    onClickTag: (String) -> Unit,
    onClickFriend: (FriendsType, Int, String) -> Unit,
    onClickShowAll: (ProfileStep.ViewAll) -> Unit,
) {
    @Composable
    fun BackPressedHeadLineTopBarInternal() {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuackImage(
                modifier = Modifier
                    .size(24.dp, 24.dp)
                    .quackClickable(onClick = onClickNotification),
                src = QuackV1Icon.Companion.Notice,
            )
            QuackImage(
                modifier = Modifier
                    .size(24.dp, 24.dp)
                    .quackClickable(onClick = onClickSetting),
                src = QuackIcon.Outlined.Setting,
            )
        }
    }

    val tags = remember(userProfile.user?.favoriteTags) {
        userProfile.user?.favoriteTags?.toImmutableList() ?: persistentListOf()
    }
    val submittedExams = remember(userProfile.createdExams) {
        userProfile.createdExams?.map(ProfileExam::toUiModel)?.toImmutableList()
            ?: persistentListOf()
    }

    ProfileScreen(
        modifier = modifier,
        userProfile = userProfile,
        isLoading = isLoading,
        editSection = {
            EditSection(
                onClickEditProfile = onClickEditProfile,
                onClickEditTag = onClickEditTag,
            )
        },
        topBar = {
            if (isAccessedProfile) {
                BackPressedHeadLineTopAppBar(
                    isLoading = isLoading,
                    title = userProfile.user?.nickname ?: "",
                    trailingContent = {
                        BackPressedHeadLineTopBarInternal()
                    },
                    onBackPressed = { navigateBack?.invoke() },
                )
            } else {
                HeadLineTopAppBar(
                    title = userProfile.user?.nickname ?: "",
                    rightIcons = {
                        BackPressedHeadLineTopBarInternal()
                    },
                )
            }
        },
        tagSection = {
            FavoriteTagSection(
                isLoading = isLoading,
                title = stringResource(id = R.string.my_favorite_tag),
                tags = tags,
                emptySection = {
                    QuackButton(
                        style = QuackButtonStyle.SecondaryLarge,
                        text = stringResource(id = R.string.add_favorite_tag),
                        onClick = onClickEditTag,
                    )
                },
                onClickTag = onClickTag,
            )
        },
        submittedExamSection = {
            ExamSection(
                isLoading = isLoading,
                icon = QuackIcon.Outlined.Create,
                title = stringResource(id = R.string.submitted_exam),
                exams = submittedExams,
                onClickExam = onClickExam,
                onClickMore = null,
                emptySection = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        EmptyText(message = stringResource(id = R.string.not_yet_submit_exam))
                        Spacer(modifier = Modifier.padding(8.dp))
                        QuackButton(
                            style = QuackButtonStyle.SecondaryLarge,
                            text = stringResource(id = R.string.make_exam),
                            onClick = onClickMakeExam,
                        )
                    }
                },
                onClickShowAll = {
                    onClickShowAll(
                        ProfileStep.ViewAll(
                            examType = ExamType.Created,
                        ),
                    )
                },
            )
        },
        onClickExam = onClickExam,
        onClickFriend = onClickFriend,
        onClickShowAll = onClickShowAll,
    )
}
