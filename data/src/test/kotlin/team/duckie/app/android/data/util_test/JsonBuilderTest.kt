/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.util_test

import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import team.duckie.app.android.data._util.buildJson
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.constant.DuckieTier
import team.duckie.app.android.domain.user.model.User

class JsonBuilderTest {
    @Test
    fun single() {
        val json = buildJson {
            "name" withString "duckie"
            "age" withInt 1
            "isDuck" withBoolean true
        }
        val expected = "{\"name\":\"duckie\",\"age\":1,\"isDuck\":true}"
        expectThat(json).isEqualTo(expected)
    }

    @Test
    fun nested() {
        val json = buildJson {
            "name" withString "duckie"
            "age" withInt 1
            "isDuck" withBoolean true
            "address" withString buildJson {
                "city" withString "seoul"
                "country" withString "korea"
                "zipCode" withInt 12345
            }
        }.replace("\\\"", "\"")
        val expected = "{\"name\":\"duckie\",\"age\":1,\"isDuck\":true," +
                "\"address\":\"{\"city\":\"seoul\",\"country\":\"korea\",\"zipCode\":12345}\"}"
        expectThat(json).isEqualTo(expected)
    }

    @Test
    fun nestedList() {
        val json = buildJson {
            "name" withString "duckie"
            "age" withInt 1
            "isDuck" withBoolean true
            "favoriteNumbers" withInts listOf(1, 2, 3, 4, 5)
        }
        val expected = "{\"name\":\"duckie\",\"age\":1,\"isDuck\":true," +
                "\"favoriteNumbers\":[1,2,3,4,5]}"
        expectThat(json).isEqualTo(expected)
    }

    @Test
    fun nestedPojo() {
        val tags = persistentListOf(
            Tag(id = 1, name = "1"),
            Tag(id = 2, name = "2"),
            Tag(id = 3, name = "3"),
        )
        val json = buildJson {
            "name" withString "duckie"
            "age" withInt 1
            "isDuck" withBoolean true
            "friends" withPojos listOf(
                User(
                    id = 0,
                    nickname = "test",
                    profileImageUrl = "test",
                    tier = DuckieTier.DuckKid,
                    favoriteTags = tags,
                    favoriteCategories = persistentListOf(
                        Category(
                            id = 1,
                            name = "1",
                            popularTags = tags,
                        ),
                        Category(
                            id = 2,
                            name = "2",
                            popularTags = tags,
                        ),
                        Category(
                            id = 3,
                            name = "3",
                            popularTags = tags,
                        ),
                    ),
                ),
                User(
                    id = 1,
                    nickname = "test2",
                    profileImageUrl = "test2",
                    tier = DuckieTier.DuckKid,
                    favoriteTags = tags,
                    favoriteCategories = persistentListOf(
                        Category(
                            id = 1,
                            name = "1",
                            popularTags = tags,
                        ),
                        Category(
                            id = 2,
                            name = "2",
                            popularTags = tags,
                        ),
                        Category(
                            id = 3,
                            name = "3",
                            popularTags = tags,
                        ),
                    ),
                ),
            )
        }
        val expected = "{\"name\":\"duckie\",\"age\":1,\"isDuck\":true," +
                "\"friends\":[{\"id\":0,\"nickname\":\"test\",\"profileImageUrl\":\"test\"," +
                "\"tier\":\"DuckKid\",\"favoriteTags\":[{\"id\":1,\"name\":\"1\"}," +
                "{\"id\":2,\"name\":\"2\"},{\"id\":3,\"name\":\"3\"}]," +
                "\"favoriteCategories\":[{\"id\":1,\"name\":\"1\",\"popularTags\":[{\"id\":1," +
                "\"name\":\"1\"},{\"id\":2,\"name\":\"2\"},{\"id\":3,\"name\":\"3\"}]}," +
                "{\"id\":2,\"name\":\"2\",\"popularTags\":[{\"id\":1,\"name\":\"1\"}," +
                "{\"id\":2,\"name\":\"2\"},{\"id\":3,\"name\":\"3\"}]},{\"id\":3,\"name\":\"3\"," +
                "\"popularTags\":[{\"id\":1,\"name\":\"1\"},{\"id\":2,\"name\":\"2\"}," +
                "{\"id\":3,\"name\":\"3\"}]}]},{\"id\":1,\"nickname\":\"test2\"," +
                "\"profileImageUrl\":\"test2\",\"tier\":\"DuckKid\",\"favoriteTags\":[{\"id\":1," +
                "\"name\":\"1\"},{\"id\":2,\"name\":\"2\"},{\"id\":3,\"name\":\"3\"}]," +
                "\"favoriteCategories\":[{\"id\":1,\"name\":\"1\",\"popularTags\":[{\"id\":1," +
                "\"name\":\"1\"},{\"id\":2,\"name\":\"2\"},{\"id\":3,\"name\":\"3\"}]}," +
                "{\"id\":2,\"name\":\"2\",\"popularTags\":[{\"id\":1,\"name\":\"1\"}," +
                "{\"id\":2,\"name\":\"2\"},{\"id\":3,\"name\":\"3\"}]},{\"id\":3,\"name\":\"3\"," +
                "\"popularTags\":[{\"id\":1,\"name\":\"1\"},{\"id\":2,\"name\":\"2\"}," +
                "{\"id\":3,\"name\":\"3\"}]}]}]}"
        expectThat(json).isEqualTo(expected)
    }
}
