package team.duckie.app.android.data.model

import team.duckie.app.data.model.constraint.CategoriesData

internal data class ContentStayTimeData(
    val user_id: String? = null,
    val categories: CategoriesData? = null,
    val search: Int? = null,
    val dm: Int? = null,
    val notification: Int? = null,
)
