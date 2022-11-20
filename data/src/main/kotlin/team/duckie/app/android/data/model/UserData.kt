package team.duckie.app.android.data.model

import team.duckie.app.data.model.constraint.BadgesData
import team.duckie.app.data.model.constraint.CategoriesData
import team.duckie.app.data.model.constraint.CollectionsData
import team.duckie.app.data.model.constraint.TagsData
import team.duckie.app.domain.model.constraint.DuckTier

internal data class UserData(
    val nick_name: String? = null,
    val account_enabled: Boolean? = null,
    val profile_url: String? = null,
    val tier: DuckTier? = null,
    val badges: BadgesData? = null,
    val like_categories: CategoriesData? = null,
    val interested_tags: TagsData? = null,
    val non_interested_tags: TagsData? = null,
    val notification_tags: TagsData? = null,
    val trade_preference_tags: TagsData? = null,
    val collections: CollectionsData? = null,
    val create_at: String? = null,
    val delete_at: String? = null,
    val banned_at: String? = null,
)
