/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:AllowMagicNumber

package team.duckie.app.android.feature.detail.screen.funding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import team.duckie.app.android.common.compose.GetHeightRatioW328H240
import team.duckie.app.android.common.compose.ui.ProfileOverlayView
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.detail.R
import team.duckie.app.android.feature.detail.common.DetailContentLayout
import team.duckie.app.android.feature.detail.common.DetailProfileSection
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackHeadLine2
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
internal fun FundingDetailContentLayout(
    modifier: Modifier = Modifier,
    state: DetailState.Success,
    tagItemClick: (String) -> Unit,
    moreButtonClick: () -> Unit,
    followButtonClick: () -> Unit,
    profileClick: (Int) -> Unit,
) {
    DetailContentLayout(
        modifier = modifier,
        state = state,
        tagItemClick = tagItemClick,
        moreButtonClick = moreButtonClick,
        additionalInfo = {
            // 펀딩 카운트 정보 Section
            FundingCounterInfoSection(state)

            // 공백
            Spacer(modifier = Modifier.height(24.dp))

            // 펀딩 출제 정보 Section
            FundingInfoSection(state)

            // 공백
            Spacer(modifier = Modifier.height(20.dp))

            // 프로필 Section
            DetailProfileSection(state, followButtonClick, profileClick)

            // 펀딩 미리보기 Section
            FundingPreviewSection(state)
        },
    )
}

/** 펀딩 카운트 정보 Section */
@Composable
private fun FundingCounterInfoSection(state: DetailState.Success) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        // 제목 및 카운트 정보
        Row {
            // 제목
            QuackTitle2(
                text = stringResource(
                    id = R.string.detail_funding_counter_info_title,
                    state.exam.remainCount,
                ),
            )

            // 공백
            Spacer(weight = 1f)

            // 만들어진 문제 개수
            QuackText(
                text = "${state.exam.problemCount ?: 0}",
                typography = QuackTypography.Title1.change(color = QuackColor.DuckieOrange),
            )
            //  / 최대 문제 개수
            QuackText(
                text = stringResource(
                    R.string.detail_funding_counter_info_value,
                    state.exam.totalProblemCount ?: 0,
                ),
                typography = QuackTypography.Title1.change(color = QuackColor.Gray1),
            )
        }

        // 공백
        Spacer(space = 8.dp)

        // 만들어진 문제 개수 / 최대 문제 개수 비율 막대 그래프
        Row(modifier = Modifier.height(4.dp)) {
            if (state.exam.problemCount != 0) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(state.exam.problemCount!!.toFloat())
                        .background(QuackColor.DuckieOrange.value),
                )
            }

            if (state.exam.remainCount != 0) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(state.exam.remainCount.toFloat())
                        .background(QuackColor.Gray3.value),
                )
            }
        }

        // 공백
        Spacer(space = 8.dp)

        // contributor 레이아웃
        Row {
            val profileUrls = state.exam.contributors?.map { it.profileImageUrl } ?: listOf()
            // 프로필 중첩 View
            ProfileOverlayView(profileUrls)

            // contributor 제목
            QuackText(
                text = stringResource(
                    id = R.string.detail_funding_counter_contributor_title,
                    profileUrls.size,
                ),
                typography = QuackTypography.Body2.change(color = QuackColor.Gray1),
            )
        }
    }
}

/** 펀딩 출제 정보 Section */
@Composable
private fun FundingInfoSection(state: DetailState.Success) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = QuackColor.Gray3.value,
                shape = RoundedCornerShape(8.dp),
            )
            .quackClickable(
                onClick = {},
                rippleEnabled = true,
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Column {
            // 제목
            QuackTitle2(
                text = stringResource(
                    id = R.string.detail_funding_info_title,
                    state.exam.user?.nickname ?: "사용자",
                ),
            )

            // 공백
            Spacer(4.dp)

            // 내용 (x문제를 검토중이에요!)
            // TODO(limsaehyun): QuackAnnotatedHeadLine2로 교체 필요
            // TODO(riflockle7): 세부 스펙 확인 뒤 활성화 필요
            // https://github.com/duckie-team/quack-quack-android/issues/442
            // QuackAnnotatedText(
            //     text = stringResource(
            //         id = R.string.detail_funding_info_description,
            //         state.exam.problemCount ?: 0,
            //     ),
            //     typography = QuackTypography.Body1,
            //     fontWeight = FontWeight.Regular,
            //     highlightTextPairs = persistentListOf(
            //         stringResource(id = R.string.detail_funding_info_description_highlighted) to {},
            //     ),
            //     underlineEnabled = false,
            // )
        }

        // 공백
        Spacer(weight = 1f)

        // 오른쪽 화살표
        QuackIcon(
            icon = Icons.Outlined.KeyboardArrowRight,
            tint = QuackColor.Gray2,
        )
    }
}

/** 펀딩 미리보기 Section */
@Composable
private fun FundingPreviewSection(state: DetailState.Success) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(QuackColor.Gray4.value)
            .padding(horizontal = 16.dp, vertical = 24.dp),
    ) {
        // 제목
        QuackHeadLine2(
            stringResource(
                id = R.string.detail_funding_preview_title,
                state.exam.title,
            ),
        )

        // 공백
        Spacer(space = 12.dp)

        // 샘플 문제 레이아웃
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .background(QuackColor.White.value)
                .padding(horizontal = 16.dp, vertical = 20.dp),
        ) {
            // 제목
            QuackHeadLine2(
                stringResource(
                    id = R.string.detail_funding_preview_sample_title,
                    state.exam.sampleQuestion?.text ?: "문제",
                ),
            )

            (state.exam.sampleQuestion as? Question.Image)?.imageUrl?.let {
                // 공백
                Spacer(space = 12.dp)

                // 썸네일 이미지
                AsyncImage(
                    model = it,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .aspectRatio(ratio = GetHeightRatioW328H240),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
            }
        }
    }
}
