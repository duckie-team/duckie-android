package land.sungbin.androidprojecttemplate.ui.splash

import android.os.Bundle
import androidx.core.app.ComponentActivity

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SplashScreen()
        }
    }
}