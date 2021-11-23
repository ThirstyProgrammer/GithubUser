package id.bachtiar.harits.githubuser.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        const val THEME_KEY = "theme_key"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

    suspend fun <T> storeValue(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    fun <T> readValue(
        key: Preferences.Key<T>,
        defaultValue: T,
    ): Flow<T> {
        return context.dataStore.data.map {
            it[key] ?: defaultValue
        }
    }
}