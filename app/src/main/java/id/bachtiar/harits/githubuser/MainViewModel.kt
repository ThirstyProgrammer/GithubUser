package id.bachtiar.harits.githubuser

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.bachtiar.harits.githubuser.base.BaseViewModel
import id.bachtiar.harits.githubuser.util.DataStoreManager
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
) : BaseViewModel() {

    var themeDrawable: Int = 0
    var isSwitchChecked: Boolean = false

    fun getThemeSetting(): LiveData<Boolean> {
        return dataStoreManager.readValue(booleanPreferencesKey(DataStoreManager.THEME_KEY), false)
            .asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) = viewModelScope.launch {
        dataStoreManager.storeValue(
            booleanPreferencesKey(DataStoreManager.THEME_KEY),
            isDarkModeActive
        )
    }
}