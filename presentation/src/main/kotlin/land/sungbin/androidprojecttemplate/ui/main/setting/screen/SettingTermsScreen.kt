package land.sungbin.androidprojecttemplate.ui.main.setting.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.ui.component.BackArrowTopAppBar
import land.sungbin.androidprojecttemplate.ui.main.setting.component.BaseAppSettingLayout
import team.duckie.quackquack.ui.component.QuackBody1

private val dummyTerms =
    """
        지방자치단체는 주민의 복리에 관한 사무를 처리하고 재산을 관리하며, 법령의 범위안에서 자치에 관한 규정을 제정할 수 있다. 헌법에 의하여 체결·공포된 조약과 일반적으로 승인된 국제법규는 국내법과 같은 효력을 가진다. 민주평화통일자문회의의 조직·직무범위 기타 필요한 사항은 법률로 정한다.

        국무회의는 대통령·국무총리와 15인 이상 30인 이하의 국무위원으로 구성한다. 제안된 헌법개정안은 대통령이 20일 이상의 기간 이를 공고하여야 한다. 국회는 의장 1인과 부의장 2인을 선출한다. 국가는 노인과 청소년의 복지향상을 위한 정책을 실시할 의무를 진다. 국가는 주택개발정책등을 통하여 모든 국민이 쾌적한 주거생활을 할 수 있도록 노력하여야 한다.

        모든 국민은 행위시의 법률에 의하여 범죄를 구성하지 아니하는 행위로 소추되지 아니하며, 동일한 범죄에 대하여 거듭 처벌받지 아니한다. 이 헌법중 공무원의 임기 또는 중임제한에 관한 규정은 이 헌법에 의하여 그 공무원이 최초로 선출 또는 임명된 때로부터 적용한다. 헌법개정안은 국회가 의결한 후 30일 이내에 국민투표에 붙여 국회의원선거권자 과반수의 투표와 투표자 과반수의 찬성을 얻어야 한다.

        국회의원의 수는 법률로 정하되, 200인 이상으로 한다. 선거에 관한 경비는 법률이 정하는 경우를 제외하고는 정당 또는 후보자에게 부담시킬 수 없다. 누구든지 체포 또는 구속을 당한 때에는 적부의 심사를 법원에 청구할 권리를 가진다. 정기회의 회기는 100일을, 임시회의 회기는 30일을 초과할 수 없다.

        국무총리는 국무위원의 해임을 대통령에게 건의할 수 있다. 의무교육은 무상으로 한다. 모든 국민은 고문을 받지 아니하며, 형사상 자기에게 불리한 진술을 강요당하지 아니한다. 언론·출판에 대한 허가나 검열과 집회·결사에 대한 허가는 인정되지 아니한다. 대통령은 법률안의 일부에 대하여 또는 법률안을 수정하여 재의를 요구할 수 없다.
    """.trimIndent()

@Composable
fun SettingTermsScreen(
    onClickBack: () -> Unit,
) {

    BackHandler {
        onClickBack()
    }

    BaseAppSettingLayout(
        topAppBar = {
            BackArrowTopAppBar(
                text = stringResource(
                    id = R.string.terms,
                )
            ) {
                onClickBack()
            }
        },
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        QuackBody1(text = dummyTerms)
    }
}