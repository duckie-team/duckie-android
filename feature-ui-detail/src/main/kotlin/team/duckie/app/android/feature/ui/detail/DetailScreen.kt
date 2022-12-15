/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.detail

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import team.duckie.quackquack.ui.R.drawable
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.detail.viewmodel.sideeffect.DetailSideEffect
import team.duckie.app.android.util.compose.CoroutineScopeContent
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.rememberToast
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSingeLazyRowTag
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSmallButtonType
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

/** 상세 화면 Screen */
@Composable
internal fun DetailScreen() = CoroutineScopeContent {
    val activity = LocalContext.current as Activity
    val viewModel = LocalViewModel.current as DetailViewModel
    val navigationBarHeight = WindowInsets.navigationBars.getBottom(LocalDensity.current)

    val toast = rememberToast()

    LaunchedEffect(Unit) {
        viewModel.sendToast("상세 화면 진입")
    }

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is DetailSideEffect.SendToast -> toast(effect.message)
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .background(color = QuackColor.White.composeColor)
            .navigationBarsPadding(),
        topBar = {
            // 상단 탭바
            // TODO(riflockle7): trailingIcon 의 경우, 추후 커스텀 composable 을 넣을 수 있게 하면 어떨지...?
            QuackTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                leadingIcon = QuackIcon.ArrowBack,
                trailingIcon = QuackIcon.More,
                onLeadingIconClick = { activity.finish() },
                onTrailingIconClick = {},
            )
        },
    ) { contentPadding ->
        Layout(
            modifier = Modifier
                .padding(contentPadding)
                .background(color = QuackColor.White.composeColor),
            content = {
                LazyColumn(
                    content = {
                        item {
                            // 그림
                            QuackImage(
                                modifier = Modifier.padding(
                                    top = 16.dp,
                                    start = 16.dp,
                                    end = 16.dp
                                ),
                                src = R.drawable.img_duckie_detail_image_dummy,
                                size = DpSize(328.dp, 240.dp)
                            )
                        }

                        item {
                            // 공백
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        item {
                            // 제목
                            QuackHeadLine2(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = "제 1회 도로 패션영역"
                            )
                        }

                        item {
                            // 공백
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        item {
                            // 내용
                            QuackBody2(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = "아 저 근데 너무 재밌을거 같아요 내 시험 최고"
                            )
                        }

                        item {
                            // 공백
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        item {
                            QuackSingeLazyRowTag(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                horizontalSpace = 4.dp,
                                items = persistentListOf(
                                    "도로", "패션", "도로패션", "도로로", "Doro Driven Design"
                                ),
                                tagType = QuackTagType.Grayscale(""),
                                onClick = {},
                            )
                            // 태그 목록
                        }
                        item {
                            // 공백
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        item {
                            // 구분선
                            QuackDivider()
                        }

                        item {
                            // 프로필 Layout
                            Row(
                                modifier = Modifier.padding(
                                    horizontal = 16.dp,
                                    vertical = 12.dp,
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // 작성자 프로필 이미지
                                QuackImage(
                                    src = drawable.quack_ic_profile_24,
                                    size = DpSize(32.dp, 32.dp)
                                )

                                // 공백
                                Spacer(modifier = Modifier.width(8.dp))

                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        // 댓글 작성자 닉네임
                                        QuackBody3(
                                            text = "닉네임",
                                            onClick = {},
                                            color = QuackColor.Black,
                                        )

                                        // 공백
                                        Spacer(modifier = Modifier.width(4.dp))
                                    }

                                    // 공백
                                    Spacer(modifier = Modifier.height(2.dp))

                                    // 응시자, 일자
                                    QuackBody3(
                                        text = activity.getString(
                                            R.string.detail_num_date, "20", "1일 전"
                                        ),
                                        color = QuackColor.Gray2
                                    )
                                }

                                // 공백
                                Spacer(modifier = Modifier.weight(1f))

                                // TODO(riflockle7): 추후 팔로우 완료 시에 대한 처리 필요
                                // 팔로우 버튼
                                QuackBody2(
                                    padding = PaddingValues(
                                        top = 8.dp,
                                        bottom = 8.dp,
                                    ),
                                    text = activity.getString(R.string.detail_follow),
                                    color = QuackColor.DuckieOrange,
                                    onClick = { },
                                )
                            }
                        }

                        item {
                            // 구분선
                            QuackDivider()
                        }

                        item {
                            // 공백
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        item {
                            // 점수 분포도 제목 Layout
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // 제목
                                QuackText(text = "점수 분포도", style = QuackTextStyle.Title2)

                                // 공백
                                Spacer(modifier = Modifier.weight(1f))

                                // 정답률 텍스트
                                QuackText(
                                    text = activity.getString(R.string.detail_right_percent, "80%"),
                                    style = QuackTextStyle.Body2
                                )
                            }
                        }

                        item {
                            // 공백
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        item {
                            // 분포도 레퍼
                            QuackText(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = "분포도 레퍼 필요",
                                style = QuackTextStyle.Body2
                            )
                        }

                        item {
                            // 하단 공백
                            Spacer(modifier = Modifier.height(53.dp))
                        }
                    }
                )

                DetailBottomLayout(
                    modifier = Modifier
                        .height(53.dp)
                        .background(color = QuackColor.White.composeColor),
                    onHeartClick = { },
                    onChallengeClick = { },
                )
            },
            measurePolicy = { measurableItems, constraints ->
                val placeableItems = measurableItems.map { measurableItem ->
                    measurableItem.measure(constraints)
                }

                var yPosition = 0

                // TODO(riflockle7): 위험성이 었어 추후 개선 필요
                //  리컴포지션이 진행될 때마다 배치 정책으로 O(N) 이 요구됨 (random-access 적용되지 않음)
                //  layoutId 가 지정되지 않아 컴포저블 배치에 변동이 생길 경우 의도하지 않은 레이아웃으로 배치될 수 있음
                //  min 값이 변경된 constraints 를 적용하지 않아 항상 match_parent 로 배치됨
                //  https://github.com/duckie-team/duckie-android/pull/110#discussion_r1045267790
                // constraints 범위 내에 각 composable 들을 배치함
                layout(constraints.maxWidth, constraints.maxHeight) {
                    for (index in 0..placeableItems.size - 2) {
                        // 각 composable x, y 좌표에 배치
                        placeableItems[index].placeRelative(x = 0, y = yPosition)

                        // y 값 변경
                        yPosition += placeableItems[index].height
                    }

                    // 상세 화면 최하단 Layout 은 맨 아래에 둠
                    yPosition = constraints.maxHeight - navigationBarHeight
                    placeableItems.last().placeRelative(x = 0, y = yPosition)
                }
            }
        )
    }
}

/** 상세 화면 최하단 Layout */
@Composable
private fun DetailBottomLayout(
    modifier: Modifier,
    onHeartClick: () -> Unit,
    onChallengeClick: () -> Unit,
) {
    Column(modifier = modifier) {
        // 구분선
        QuackDivider()

        // TODO(riflockle7): 추후 Layout 을 활용해 처리하기
        Row(
            modifier = Modifier
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // 좋아요 버튼
            QuackImage(
                src = drawable.quack_ic_heart_24,
                size = DpSize(24.dp, 24.dp),
                onClick = onHeartClick,
            )

            // 공백
            Spacer(modifier = Modifier.weight(1f))

            // 버튼
            QuackSmallButton(
                text = "하기싫음 하지마세요",
                type = QuackSmallButtonType.Fill,
                enabled = true,
                onClick = onChallengeClick,
            )
        }
    }
}
