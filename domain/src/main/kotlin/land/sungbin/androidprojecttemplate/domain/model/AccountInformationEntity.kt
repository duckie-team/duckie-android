package land.sungbin.androidprojecttemplate.domain.model

import land.sungbin.androidprojecttemplate.domain.constants.AccountType

data class AccountInformationEntity(
    val accountType: AccountType,
    val email: String,
)