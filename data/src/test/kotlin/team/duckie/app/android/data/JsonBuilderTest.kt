/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data

import kotlinx.collections.immutable.persistentListOf
import org.junit.Ignore
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import team.duckie.app.android.data._util.buildJson
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.DuckPower
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserStatus

// TODO(jisungbin): 요 파일 json 포메팅 해서 다 수정해주세요
@Ignore("jsonString 포메팅 완료한 뒤에 다시 활성화하기")
class JsonBuilderTest {
    @Test
    fun single() {
        val actual = buildJson {
            "name" withString "duckie"
            "age" withInt 1
            "isDuck" withBoolean true
        }

        val expected = "{\"name\":\"duckie\",\"age\":1,\"isDuck\":true}"

        expectThat(actual).isEqualTo(expected)
    }

    @Test
    fun nested() {
        val actual = buildJson {
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

        expectThat(actual).isEqualTo(expected)
    }

    @Test
    fun nestedList() {
        val actual = buildJson {
            "name" withString "duckie"
            "age" withInt 1
            "isDuck" withBoolean true
            "favoriteNumbers" withInts listOf(1, 2, 3, 4, 5)
        }

        val expected = "{\"name\":\"duckie\",\"age\":1,\"isDuck\":true," +
                "\"favoriteNumbers\":[1,2,3,4,5]}"

        expectThat(actual).isEqualTo(expected)
    }

    @Test
    fun nestedPojo() {
        val tags = persistentListOf(Tag(id = 1, name = "tag"))
        val actual = buildJson {
            "name" withString "duckie"
            "age" withInt 1
            "isDuck" withBoolean true
            "friends" withPojos listOf(
                User(
                    id = 0,
                    nickname = "test",
                    profileImageUrl = "test",
                    status = UserStatus.NEW,
                    duckPower = DuckPower(
                        id = 1,
                        tier = "도로 패션 20%",
                        tag = Tag(
                            id = 1,
                            name = "도로패션",
                        ),
                    ),
                    follow = null,
                    favoriteTags = tags,
                    favoriteCategories = persistentListOf(
                        Category(
                            id = 1,
                            name = "1",
                            thumbnailUrl = "thumbnailUrl",
                            popularTags = tags,
                        ),
                    ),
                    permissions = null,
                    introduction = null,
                ),
            )
        }

        val expected = "{\"name\":\"duckie\",\"age\":1,\"isDuck\":true," +
                "\"friends\":[{\"id\":0,\"nickname\":\"test\",\"profileImageUrl\":\"test\",\"status\":\"NEW\"," +
                "\"duckPower\":{\"id\":1,\"tier\":\"도로 패션 20%\",\"tag\":{\"id\":1,\"name\":\"도로패션\"}}," +
                "\"follow\":null," +
                "\"favoriteTags\":[{\"id\":1,\"name\":\"tag\"}]," +
                "\"favoriteCategories\":[{\"id\":1,\"name\":\"1\",\"thumbnailUrl\":\"thumbnailUrl\"," +
                "\"popularTags\":[{\"id\":1,\"name\":\"tag\"}]}]," +
                "\"permissions\":null}]}"

        expectThat(actual).isEqualTo(expected)
    }
}
