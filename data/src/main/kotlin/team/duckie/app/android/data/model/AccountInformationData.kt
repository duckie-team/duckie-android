package team.duckie.app.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import team.duckie.app.android.domain.constants.AccountType

@Entity
data class AccountInformationData(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    val accountType: team.duckie.app.android.domain.constants.AccountType,
    val email: String,
)
