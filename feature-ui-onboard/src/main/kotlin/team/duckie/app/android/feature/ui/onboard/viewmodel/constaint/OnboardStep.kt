package team.duckie.app.android.feature.ui.onboard.viewmodel.constaint

import team.duckie.app.android.util.kotlin.AllowMagicNumber

@AllowMagicNumber
enum class OnboardStep(private val index: Int) {
    Login(0),
    Profile(1),
    Category(2),
    Tag(3);

    operator fun minus(previous: Int): OnboardStep {
        return values()[index - previous]
    }

    operator fun plus(next: Int): OnboardStep {
        return values()[index + next]
    }
}
