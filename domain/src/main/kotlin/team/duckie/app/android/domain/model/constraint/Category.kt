@file:Suppress("unused")

package team.duckie.app.android.domain.model.constraint

/** 덕질 분야 */
enum class Category(
    val index: Int,
    val message: String,
) {
    Celebrity(
        index = 0,
        message = "연애인",
    ),
    Movie(
        index = 1,
        message = "영화",
    ),
    Animation(
        index = 2,
        message = "만화/애니",
    ),
    WebToon(
        index = 3,
        message = "웹툰",
    ),
    Game(
        index = 4,
        message = "게임",
    ),
    Military(
        index = 5,
        message = "밀리터리",
    ),
    IT(
        index = 6,
        message = "IT",
    ),
}
