package team.duckie.app.android.data.model

internal data class ReportData(
    val id: String? = null,
    val reporterId: String? = null,
    val targetId: String? = null,
    val targetFeedId: String? = null,
    val message: String? = null,
    val checked: Boolean? = null,
)
