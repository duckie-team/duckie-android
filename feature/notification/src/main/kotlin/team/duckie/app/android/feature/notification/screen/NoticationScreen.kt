/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.notification.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.NoItemScreen
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.quack.QuackCrossfade
import team.duckie.app.android.common.compose.ui.quack.todo.QuackTopAppBar
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.feature.notification.R
import team.duckie.app.android.feature.notification.viewmodel.NotificationViewModel
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.ArrowBack
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.material.shape.SquircleShape
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.sugar.QuackBody2
import team.duckie.quackquack.ui.sugar.QuackBody3

@Composable
internal fun NotificationScreen(
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = activityViewModel(),
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        QuackTopAppBar(
            leadingIcon = QuackIcon.Outlined.ArrowBack,
            leadingText = stringResource(id = R.string.notification),
            onLeadingIconClick = viewModel::clickBackPress,
        )
        QuackCrossfade(
            targetState = state.notifications.isEmpty(),
        ) { isEmpty ->
            when {
                state.isError -> {
                    ErrorScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding(),
                        onRetryClick = viewModel::getNotifications,
                    )
                }

                isEmpty -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(space = 120.dp)
                        NoItemScreen(
                            title = stringResource(id = R.string.empty_notfications),
                            description = stringResource(id = R.string.check_notifications_after_activity),
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 20.dp)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(28.dp),
                    ) {
                        items(
                            items = state.notifications,
                            key = { it.id },
                        ) { notification ->
                            with(notification) {
                                NotificationItem(
                                    thumbnailUrl = thumbnailUrl,
                                    body = body,
                                    createdAt = createdAt,
                                    isLoading = state.isLoading,
                                    onClick = { viewModel.clickNotification(notification.id) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationItem(
    thumbnailUrl: String,
    body: String,
    createdAt: String,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .quackClickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        QuackImage(
            modifier = Modifier
                .size(36.dp)
                .skeleton(
                    visible = isLoading,
                    shape = SquircleShape,
                ),
            src = thumbnailUrl,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            QuackBody2(
                modifier = Modifier.skeleton(visible = isLoading),
                text = body,
            )
            QuackBody3(
                modifier = Modifier.skeleton(visible = isLoading),
                text = createdAt,
            )
        }
    }
}
