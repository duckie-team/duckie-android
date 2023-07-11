/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.ui.quack.todo.QuackLazyVerticalGridTag
import team.duckie.app.android.common.compose.ui.quack.todo.QuackSingeLazyRowTag
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon as QuackV1Icon

/**
 * 관심태그 섹션
 *
 * @param modifier [Modifier]
 * @param title 상단 타이틀 제목
 * @param horizontalPadding 좌우 패딩. 기본값은 0dp
 * @param verticalArrangement Column 내 각 컴포넌트 내 패딩. 기본값은 0dp
 * @param trailingIcon 태그 항목 뒤에 덧붙는 아이콘
 * @param onTrailingClick 태그 항목 뒤에 덧붙는 아이콘 클릭 시 처리
 * @param singleLine 태그 목록이 한 줄로 표현되는지 여부
 * @param emptySection 태그 목록이 비어있을 때 보여줄 화면
 * @param tags 보여줄 태그 목록
 * @param onTagClick 태그 클릭 시 처리
 * @param onAddTagClick 태그 추가 버튼 클릭 시 처리
 * @param addButtonTitle 추가 버튼 제목
 */
@Suppress("unused")
@Composable
fun FavoriteTagSection(
    modifier: Modifier = Modifier,
    title: String,
    horizontalPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(0.dp),
    trailingIcon: QuackIcon? = null,
    onTrailingClick: ((Int) -> Unit)? = null,
    singleLine: Boolean = true,
    emptySection: @Composable () -> Unit,
    tags: ImmutableList<String>,
    onTagClick: (Int) -> Unit,
    onAddTagClick: (() -> Unit)? = null,
    addButtonTitle: String? = null,
) {
    val tagList = remember(tags) { tags }

    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
    ) {
        // 제목
        QuackTitle2(
            text = title,
            modifier = Modifier.padding(horizontalPadding),
        )

        if (tags.isEmpty()) {
            // 태그가 없을 경우 표시되는 Section
            emptySection()
        } else {
            if (singleLine) {
                // 한 줄로 표현되는 태그 목록
                QuackSingeLazyRowTag(
                    items = tagList,
                    contentPadding = horizontalPadding,
                    tagType = QuackTagType.Circle(trailingIcon),
                    onClick = { index -> onTagClick(index) },
                )
            } else {
                // 여러 줄로 표현되는 태그 목록
                QuackLazyVerticalGridTag(
                    contentPadding = horizontalPadding,
                    horizontalSpace = 4.dp,
                    items = tagList,
                    tagType = QuackTagType.Circle(trailingIcon),
                    onClick = { index -> onTagClick(index) },
                    itemChunkedSize = 4,
                )
            }
        }

        // 추가 버튼
        onAddTagClick?.let { onAddClick ->
            require(!addButtonTitle.isNullOrEmpty())

            QuackHeadLine2(
                text = addButtonTitle,
                padding = horizontalPadding,
                color = QuackColor.DuckieOrange,
                onClick = onAddClick,
            )
        }
    }
}
