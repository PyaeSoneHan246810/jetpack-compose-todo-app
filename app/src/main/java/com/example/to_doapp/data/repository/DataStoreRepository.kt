package com.example.to_doapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCES_NAME)
@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val sortStateKey = stringPreferencesKey(Constants.SORT_STATE_KEY)
    }

    private val dataStore = context.dataStore

    suspend fun saveSortState(sortState: String) {
        dataStore.edit {  mutablePreferences ->
            mutablePreferences[sortStateKey] = sortState
        }
    }

    fun readSortState(): Flow<String> {
        return dataStore
            .data
            .catch {  exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw  exception
                }
            }
            .map {  preferences ->
                val sortState = preferences[sortStateKey] ?: Priority.NONE.title
                sortState
            }
    }
}