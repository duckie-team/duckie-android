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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.util.compose.CoroutineScopeContent
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
    val navigationBarHeight = WindowInsets.navigationBars.getBottom(LocalDensity.current)

    Scaffold(
        topBar = {
            // 상단 탭바
            // TODO(riflockle7) trailingIcon 의 경우, 추후 커스텀 composable 을 넣을 수 있게 하면 어떨지...?
            QuackTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                leadingIcon = QuackIcon.ArrowBack,
                trailingIcon = QuackIcon.More,
                onLeadingIconClick = { activity.finish() },
                onTrailingIconClick = {},
            )
        },
        bottomBar = {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        },
    ) { contentPadding ->
        Layout(
            modifier = Modifier
                .padding(contentPadding)
                .background(color = Color(activity.getColor(R.color.detail_background))),
            content = {
                LazyColumn(
                    content = {
                        item {
                            // 공백
                            Spacer(modifier = Modifier.height(16.dp))

                            // 그림
                            QuackImage(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                src = R.drawable.img_duckie_detail_image_dummy,
                                size = DpSize(328.dp, 240.dp)
                            )

                            // 공백
                            Spacer(modifier = Modifier.height(12.dp))

                            // 제목
                            QuackHeadLine2(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = "제 1회 도로 패션영역"
                            )

                            // 공백
                            Spacer(modifier = Modifier.height(8.dp))

                            // 내용
                            QuackBody2(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = "아 저 근데 너무 재밌을거 같아요 내 시험 최고"
                            )

                            // 공백
                            Spacer(modifier = Modifier.height(12.dp))

                            // 태그 목록
                            QuackSingeLazyRowTag(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                items = persistentListOf(
                                    "도로",
                                    "패션",
                                    "도로패션",
                                    "도로로",
                                    "Doro Driven Design"
                                ),
                                tagType = QuackTagType.Round,
                                onClick = {},
                            )

                            // 공백
                            Spacer(modifier = Modifier.height(24.dp))

                            // 구분선
                            QuackDivider()

                            // 프로필 Layout
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // 작성자 프로필 이미지
                                QuackImage(
                                    src = team.duckie.quackquack.ui.R.drawable.quack_ic_profile_24,
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
                                            R.string.detail_num_date,
                                            "20",
                                            "1일 전"
                                        ),
                                        color = QuackColor.Gray2
                                    )
                                }

                                // 공백
                                Spacer(modifier = Modifier.weight(1f))

                                // TODO(sangwo-o.lee) 단순 텍스트로만 이루어진 버튼 있는지 확인 필요
                                QuackSmallButton(
                                    text = activity.getString(R.string.detail_follow),
                                    type = QuackSmallButtonType.Border,
                                    enabled = true,
                                    onClick = {},
                                )
                            }

                            // 구분선
                            QuackDivider()

                            // 공백
                            Spacer(modifier = Modifier.height(24.dp))

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

                            // 공백
                            Spacer(modifier = Modifier.height(8.dp))

                            // 분포도 레퍼
                            QuackText(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = "분포도 레퍼 필요",
                                style = QuackTextStyle.Body2
                            )

                            // 하단 공백
                            Spacer(modifier = Modifier.height(53.dp))
                        }
                    }
                )

                DetailBottomLayout(
                    modifier = Modifier
                        .height(53.dp)
                        .background(
                            color = Color(
                                ContextCompat.getColor(activity, R.color.detail_background)
                            )
                        ),
                    onHeartClick = { },
                    onChallengeClick = { },
                )
            },
            measurePolicy = { measurableItems, constraints ->
                val placeableItems = measurableItems.map { measurableItem ->
                    measurableItem.measure(constraints)
                }

                var yPosition = 0

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
fun DetailBottomLayout(
    modifier: Modifier,
    onHeartClick: () -> Unit,
    onChallengeClick: () -> Unit,
) {
    Column(modifier = modifier) {
        // 구분선
        QuackDivider()

        Row(
            modifier = Modifier
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 좋아요 버튼
            QuackImage(
                src = team.duckie.quackquack.ui.R.drawable.quack_ic_heart_24,
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
