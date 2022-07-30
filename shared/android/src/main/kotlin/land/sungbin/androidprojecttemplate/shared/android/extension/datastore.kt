package land.sungbin.androidprojecttemplate.shared.android.extension

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

// TODO: set your DataStore name
private const val DataStoreName = "TODO"

val Context.dataStore by preferencesDataStore(
    name = DataStoreName
)
