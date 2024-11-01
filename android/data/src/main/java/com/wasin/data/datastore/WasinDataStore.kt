package com.wasin.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

val Context.dataStore by preferencesDataStore("wasin_data")

class WasinDataStore @Inject constructor(@ApplicationContext val context: Context) {
    private val store = context.dataStore
    fun setData(key: String, value: String){
        runBlocking(Dispatchers.IO) {
            store.edit{ it[stringPreferencesKey(key)] = value }
        }
    }
    fun getData(key: String): String {
        return runBlocking(Dispatchers.IO){
            store.data.map{ it[stringPreferencesKey(key)] ?: "" }.first()
        }
    }
    fun clear(key: String){
        runBlocking(Dispatchers.IO) {
            val prefKey = stringPreferencesKey(key)
            store.edit{ it.remove(prefKey) }
        }
    }
}
