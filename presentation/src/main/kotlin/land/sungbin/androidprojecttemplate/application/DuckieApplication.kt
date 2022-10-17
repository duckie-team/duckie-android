package land.sungbin.androidprojecttemplate.application

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import land.sungbin.androidprojecttemplate.R

@HiltAndroidApp
class DuckieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
    }

}