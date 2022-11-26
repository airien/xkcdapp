package no.hanne.xkcd.core.repository

import android.app.Application
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

interface DatastoreRepository {
    suspend fun getFavourites(): List<Int>
    suspend fun storeFavourite(num: Int)
    suspend fun removeFavourite(num: Int)
    suspend fun getLatest(): Int?
    suspend fun storeLatest(num: Int)
}

class DatastoreRepositoryImpl @Inject constructor(
    application: Application,
    dataStoreFilename: String
) : DatastoreRepository {
    private val dataStore = getDataStore(
        application = application,
        dataStoreFilename = dataStoreFilename
    )

    override suspend fun getFavourites(): List<Int> {
        val favourites = get(PreferencesKeys.FAVORITES)
        if (favourites.isNullOrEmpty()) return listOf()
        return favourites?.let { f ->
            listOf(*f.split(",").toTypedArray()).map { it.trim().toInt() }
        } ?: run { mutableListOf() }
    }

    override suspend fun storeFavourite(num: Int) {
        val list = getFavourites().toMutableList()
        if (!list.contains(num)) {
            list.add(num)
        }
        set(PreferencesKeys.FAVORITES, list.joinToString(separator = ","))
    }

    override suspend fun removeFavourite(num: Int) {
        val list = getFavourites().toMutableList()
        list.remove(num)
        set(PreferencesKeys.FAVORITES, list.joinToString())
    }

    override suspend fun getLatest(): Int? = get(PreferencesKeys.LATEST)

    override suspend fun storeLatest(num: Int) {
        set(PreferencesKeys.LATEST, num)
    }
    private fun getDataStore(
        application: Application,
        dataStoreFilename: String
    ) =
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {
                application.preferencesDataStoreFile(dataStoreFilename)
            }
        )
    private suspend fun <T> get(preferencesKey: Preferences.Key<T>) =
        try {
            dataStore.data.first()[preferencesKey]
        } catch (e: Throwable) {
            Timber.e(e)
            null
        }

    private suspend fun <T> set(preferencesKey: Preferences.Key<T>, value: T) {
        try {
            dataStore.edit { preferences ->
                preferences[preferencesKey] = value
            }
        } catch (e: Throwable) {
            Timber.e(e)
        }
    }
    private suspend fun <T> clear(preferencesKey: Preferences.Key<T>) {
        try {
            dataStore.edit { preferences ->
                preferences.remove(preferencesKey)
            }
        } catch (e: Throwable) {
            Timber.e(e)
        }
    }
    private object PreferencesKeys {
        val FAVORITES = stringPreferencesKey("FAVORITES")
        val LATEST = intPreferencesKey("LATEST")
    }
}
