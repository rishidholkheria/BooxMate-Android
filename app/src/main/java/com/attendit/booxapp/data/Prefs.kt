package com.attendit.booxapp.data

import android.content.Context
import android.content.SharedPreferences

object Prefs {
    const val PREFS_NAME = "AttendanceSemList"
    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun getEditor(context: Context): SharedPreferences.Editor {
        return getPrefs(context).edit()
    }

    fun getStringPrefs(context: Context, key: String?): String? {
        return getPrefs(context).getString(key, "")
    }

    fun getStringPrefs(context: Context, key: String?, defauiltVal: String?): String? {
        return getPrefs(context).getString(key, defauiltVal)
    }

    @JvmStatic
    fun putStringPrefs(context: Context, key: String?, value: String?) {
        getEditor(context).putString(key, value).commit()
    }

    fun getBooleanPrefs(context: Context, key: String?): Boolean {
        return getPrefs(context).getBoolean(key, false)
    }

    fun putBooleanPrefs(context: Context, key: String?, value: Boolean) {
        getEditor(context).putBoolean(key, value).commit()
    }

    fun getIntPrefs(context: Context, key: String?): Int {
        return getPrefs(context).getInt(key, 0)
    }

    fun putIntPrefs(context: Context, key: String?, value: Int?) {
        getEditor(context).putInt(key, value!!).commit()
    }

    fun clearPrefs(context: Context) {
        getEditor(context).clear().commit()
    }
}