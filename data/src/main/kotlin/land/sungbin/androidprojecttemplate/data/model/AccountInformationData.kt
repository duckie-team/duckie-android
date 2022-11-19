package land.sungbin.androidprojecttemplate.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import land.sungbin.androidprojecttemplate.domain.constants.AccountType

@Entity
data class AccountInformationData(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    val accountType: String,
    val email: String,
)
