package land.sungbin.androidprojecttemplate.data.model

import land.sungbin.androidprojecttemplate.data.model.util.NewField

internal data class HeartData(
    @NewField val type: Int? = null,
    val feedId: String? = null,
    val userId: String? = null,
)

