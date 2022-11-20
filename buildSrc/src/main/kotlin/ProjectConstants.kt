// enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS") 버그이씀 (모듈 많아지면 새로운 모듈 인식 안됨)
@Suppress("SpellCheckingInspection")
object ProjectConstants {
    const val Data = ":data"
    const val Domain = ":domain"
    const val SharedAndroid = ":shared:android"
    const val SharedDomain = ":shared:domain"
    const val SharedCompose = ":shared:compose"
    const val Presentation = ":presentation"
}
