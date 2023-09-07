/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)
// TODO(limsaehyun): The function onCreate is too long (127).
//  The maximum length is 100. [LongMethod] 대응 필요
@file:Suppress("LongMethod")

package team.duckie.app.android.feature.search.screen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.android.deeplink.DynamicLinkHelper
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.android.ui.finishWithAnimation
import team.duckie.app.android.common.android.ui.popStringExtra
import team.duckie.app.android.common.compose.collectAndHandleState
import team.duckie.app.android.common.compose.systemBarPaddings
import team.duckie.app.android.common.compose.ui.DuckieCircularProgressIndicator
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.constant.SharedIcon
import team.duckie.app.android.common.compose.ui.dialog.DuckieSelectableBottomSheetDialog
import team.duckie.app.android.common.compose.ui.dialog.DuckieSelectableType
import team.duckie.app.android.common.compose.ui.dialog.ReportDialog
import team.duckie.app.android.common.compose.ui.quack.QuackNoUnderlineTextField
import team.duckie.app.android.common.compose.util.addFocusCleaner
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.feature.search.R
import team.duckie.app.android.feature.search.constants.SearchResultStep
import team.duckie.app.android.feature.search.constants.SearchStep
import team.duckie.app.android.feature.search.viewmodel.SearchViewModel
import team.duckie.app.android.feature.search.viewmodel.sideeffect.SearchSideEffect
import team.duckie.app.android.navigator.feature.detail.DetailNavigator
import team.duckie.app.android.navigator.feature.profile.ProfileNavigator
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.ArrowBack
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.material.theme.QuackTheme
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.plugin.interceptor.textfield.QuackTextFieldFontFamilyRemovalPlugin
import team.duckie.quackquack.ui.plugin.rememberQuackPlugins
import javax.inject.Inject

internal val SearchHorizontalPadding = PaddingValues(horizontal = 16.dp)

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    @Inject
    lateinit var detailNavigator: DetailNavigator

    @Inject
    lateinit var profileNavigator: ProfileNavigator

    private val vm: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.popStringExtra(Extras.SearchTag)?.let { str ->
            vm.updateSearchKeyword(
                keyword = str,
                debounce = false,
            )
        }

        setContent {
            val state = vm.collectAsState().value
            val focusRequester = remember { FocusRequester() }
            val bottomSheetDialogState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
            val coroutineScope = rememberCoroutineScope()
            val keyboardController = LocalSoftwareKeyboardController.current
            val searchText by vm.searchText.collectAsStateWithLifecycle()

            vm.searchUsers.collectAndHandleState(handleLoadStates = vm::checkError)
            val searchExams =
                vm.searchExams.collectAndHandleState(handleLoadStates = vm::checkError)

            LaunchedEffect(key1 = vm) {
                vm.container.sideEffectFlow
                    .onEach {
                        handleSideEffect(it, searchExams)
                    }
                    .launchIn(this)
            }

            LaunchedEffect(key1 = state.searchAutoFocusing) {
                if (state.searchAutoFocusing) {
                    focusRequester.requestFocus()
                }
            }

            QuackTheme(
                plugins = rememberQuackPlugins {
                    +QuackTextFieldFontFamilyRemovalPlugin
                },
            ) {
                ReportDialog(
                    visible = state.reportDialogVisible,
                    onClick = { vm.updateReportDialogVisible(false) },
                    onDismissRequest = { vm.updateReportDialogVisible(false) },
                )
                DuckieSelectableBottomSheetDialog(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                        .navigationBarsPadding(),
                    bottomSheetState = bottomSheetDialogState,
                    closeSheet = {
                        coroutineScope.launch {
                            bottomSheetDialogState.hide()
                        }
                    },
                    onReport = vm::report,
                    onCopyLink = vm::copyExamDynamicLink,
                    types = persistentListOf(
                        DuckieSelectableType.CopyLink,
                        DuckieSelectableType.Report,
                    ),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(QuackColor.White.value)
                            .addFocusCleaner(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column {
                            systemBarPaddings
                            SearchTextFieldTopBar(
                                searchKeyword = searchText,
                                onSearchKeywordChanged = { keyword ->
                                    vm.updateSearchKeyword(keyword = keyword)
                                },
                                onPrevious = {
                                    finishWithAnimation()
                                },
                                clearSearchKeyword = {
                                    vm.clearSearchKeyword()
                                },
                                focusRequester = focusRequester,
                            )
                            AnimatedContent(
                                targetState = state.searchStep,
                                label = "AnimatedContent",
                            ) { step ->
                                when (step) {
                                    SearchStep.Search -> SearchScreen(vm = vm)
                                    SearchStep.SearchResult -> {
                                        if (state.isSearchProblemError &&
                                            state.tagSelectedTab == SearchResultStep.DuckExam
                                        ) {
                                            ErrorScreen(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .statusBarsPadding(),
                                                false,
                                                onRetryClick = {
                                                    vm.fetchSearchExams(searchText)
                                                },
                                            )
                                        } else if (state.isSearchUserError &&
                                            state.tagSelectedTab == SearchResultStep.User
                                        ) {
                                            ErrorScreen(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .statusBarsPadding(),
                                                false,
                                                onRetryClick = {
                                                    vm.fetchSearchUsers(searchText)
                                                },
                                            )
                                        } else {
                                            SearchResultScreen(
                                                navigateDetail = { examId ->
                                                    vm.navigateToDetail(examId = examId)
                                                },
                                                openBottomSheet = { examId ->
                                                    vm.setTargetExamId(examId = examId)
                                                    coroutineScope.launch {
                                                        keyboardController?.hide()
                                                        bottomSheetDialogState.show()
                                                    }
                                                },
                                                onSearchComplete = {
                                                    focusRequester.freeFocus()
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if (state.isSearchLoading) {
                            DuckieCircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }

    private fun handleSideEffect(
        sideEffect: SearchSideEffect,
        examPagingItems: LazyPagingItems<Exam>,
    ) {
        when (sideEffect) {
            is SearchSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
            }

            is SearchSideEffect.NavigateToDetail -> {
                detailNavigator.navigateFrom(
                    activity = this@SearchActivity,
                    intentBuilder = {
                        putExtra(Extras.ExamId, sideEffect.examId)
                    },
                )
            }

            is SearchSideEffect.NavigateToUserProfile -> {
                profileNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.UserId, sideEffect.userId)
                    },
                )
            }

            is SearchSideEffect.SendToast -> {
                Toast.makeText(this, sideEffect.message, Toast.LENGTH_SHORT).show()
            }

            is SearchSideEffect.CopyDynamicLink -> {
                DynamicLinkHelper.createAndShareLink(this, sideEffect.examId)
            }

            SearchSideEffect.ExamRefresh -> {
                examPagingItems.refresh()
            }
        }
    }
}

@Composable
private fun SearchTextFieldTopBar(
    modifier: Modifier = Modifier,
    searchKeyword: String,
    onSearchKeywordChanged: (String) -> Unit,
    clearSearchKeyword: () -> Unit,
    onPrevious: () -> Unit,
    focusRequester: FocusRequester,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(
                horizontal = 16.dp,
                vertical = 6.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackIcon(
            modifier = Modifier.quackClickable(onClick = onPrevious),
            icon = QuackIcon.Outlined.ArrowBack,
        )
        Spacer(space = 8.dp)
        QuackNoUnderlineTextField(
            modifier = Modifier.focusRequester(focusRequester),
            text = searchKeyword,
            onTextChanged = onSearchKeywordChanged,
            placeholderText = stringResource(id = R.string.try_search),
            trailingIcon = if (searchKeyword.isNotEmpty()) SharedIcon.ic_textfield_delete_16 else null,
            trailingIconOnClick = clearSearchKeyword,
        )
    }
}
