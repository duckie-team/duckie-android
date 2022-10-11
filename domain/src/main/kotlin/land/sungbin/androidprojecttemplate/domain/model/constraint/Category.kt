package land.sungbin.androidprojecttemplate.domain.model.constraint

/** 덕질 분야 */
enum class Category(
    val message: String,
) {
    Celebrity("연애인"),
    Movie("영화"),
    Animation("만화/애니"),
    WebToon("웹툰"),
    Game("게임"),
    Military("밀리터리"),
    IT("IT"),
}
