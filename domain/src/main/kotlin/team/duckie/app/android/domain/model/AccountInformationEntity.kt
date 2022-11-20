package team.duckie.app.android.domain.model

import team.duckie.app.android.domain.constants.AccountType

data class AccountInformationEntity(
    val accountType: team.duckie.app.android.domain.constants.AccountType,
    val email: String,
)
