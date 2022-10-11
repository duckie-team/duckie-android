package land.sungbin.androidprojecttemplate.domain.model.constraint

/** 덕질 분야 */
enum class Category(
    val index: Int,
    val message: String,
) {
    Celebrity(0, "연애인"),
    Movie(1, "영화"),
    Animation(2, "만화/애니"),
    WebToon(3, "웹툰"),
    Game(4, "게임"),
    Military(5, "밀리터리"),
    IT(6, "IT"),
}
