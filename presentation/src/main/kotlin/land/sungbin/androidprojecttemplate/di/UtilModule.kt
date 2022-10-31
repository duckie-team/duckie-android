package land.sungbin.androidprojecttemplate.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import land.sungbin.androidprojecttemplate.util.PrefUtil
import land.sungbin.androidprojecttemplate.util.UserHolder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilModule {

    @Singleton
    @Provides
    fun providePrefUtil(@ApplicationContext context : Context) = PrefUtil(context)

    @Singleton
    @Provides
    fun provideUserHolder(@ApplicationContext context: Context) = UserHolder(providePrefUtil(context))
}