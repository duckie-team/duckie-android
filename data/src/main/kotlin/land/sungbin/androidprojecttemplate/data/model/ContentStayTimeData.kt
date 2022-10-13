package land.sungbin.androidprojecttemplate.data.model

import land.sungbin.androidprojecttemplate.data.model.constraint.CategoriesData

internal data class ContentStayTimeData(
    val userId: String? = null,
    val categories: CategoriesData? = null,
    val search: Int? = null,
    val dm: Int? = null,
    val notification: Int? = null,
)
