package team.duckie.app.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SettingData(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    val activityNotification: Boolean,

    val messageNotification: Boolean,
)
