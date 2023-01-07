/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.dummy

import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag

object CategoryDummyResponse {
    const val RawData = """
        {
          "categories": [
            {
              "id": 1,
              "name": "연예인",
              "thumbnailUrl": "thumbnailUrl",
              "popularTags": [
                {
                  "name": "강호동",
                  "id": 8
                },
                {
                  "name": "송민호",
                  "id": 7
                }
              ]
            },
            {
              "id": 3,
              "name": "영화",
              "thumbnailUrl": "thumbnailUrl",
              "popularTags": []
            }
          ]
        }
    """

    val DomainData = listOf(
        Category(
            id = 1,
            name = "연예인",
            thumbnailUrl = "thumbnailUrl",
            popularTags = persistentListOf(
                Tag(
                    id = 8,
                    name = "강호동",
                ),
                Tag(
                    id = 7,
                    name = "송민호",
                ),
            ),
        ),
        Category(
            id = 3,
            name = "영화",
            thumbnailUrl = "thumbnailUrl",
            popularTags = persistentListOf(),
        ),
    )
}
