package com.android.testapp.data.sources.local.preferences

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import java.io.Serializable
import java.lang.reflect.Type
import java.util.*

abstract class BaseSharedPreferences(context: Context) {

    private var preferences =
        context.getSharedPreferences(PREFERENCES_FILE_NAME, Activity.MODE_PRIVATE)
    private val gson = Gson()

    protected val all: Map<String, *>
        get() = preferences.all

    protected fun getString(key: String, defaultValue: String?): String? {
        return preferences.getString(key, defaultValue)
    }

    protected fun setString(key: String, value: String?) {
        preferences.edit().putString(key, value).apply()
    }

    protected fun getInt(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    protected fun setInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    protected fun getLong(key: String, defaultValue: Long): Long {
        return preferences.getLong(key, defaultValue)
    }

    protected fun setLong(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    protected fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    protected fun setBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    protected fun setSerializable(key: String, value: Serializable?) {
        preferences.edit().putString(key, gson.toJson(value)).apply()
    }

    protected fun <T> getSerializable(key: String, type: Class<T>): T? {
        val value = preferences.getString(key, null)
        return if (value.isNullOrEmpty()) {
            null
        } else gson.fromJson(value, type)
    }

    protected fun getStringSet(key: String, defaultValue: Set<String>): MutableSet<String>? {
        return preferences.getStringSet(key, defaultValue)
    }

    private fun setStringSet(key: String, stringSet: Set<String>) {
        preferences.edit().putStringSet(key, stringSet).apply()
    }

    protected fun <T> setList(key: String, list: List<T>) {
        val json = gson.toJson(list)
        preferences.edit().putString(key, json).apply()
    }

    protected fun <T> getList(key: String, type: Type): List<T> {
        val jsonString = preferences.getString(key, "")!!
        if (jsonString.isNotEmpty()) {
            return gson.fromJson(jsonString, type)
        }
        return emptyList()
    }

    protected fun addStringToSet(key: String, value: String) {
        val stringSet = getStringSet(key, HashSet()) ?: HashSet()
        stringSet.add(value)
        setStringSet(key, stringSet)
    }

    protected fun removeStringFromSet(key: String, value: String) {
        val stringSet = getStringSet(key, HashSet()) ?: return
        stringSet.remove(value)
        setStringSet(key, stringSet)
    }

    protected operator fun contains(key: String): Boolean {
        return preferences.contains(key)
    }

    protected fun clear(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun clearAllEntries() {
        preferences.edit().clear().apply()
    }

    companion object {
        const val PREFERENCES_FILE_NAME = "WeatherAppUserPreferences"
    }
}
