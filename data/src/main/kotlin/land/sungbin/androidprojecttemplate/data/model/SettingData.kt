package land.sungbin.androidprojecttemplate.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Entity
data class SettingData(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    val activityNotification: Boolean,

    val messageNotification: Boolean,
)
