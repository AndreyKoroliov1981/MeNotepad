package com.example.myapplication

import android.content.Context
import android.content.Context.MODE_PRIVATE

private const val APP_PREFERENCES = "mytext"
private const val APP_PREFERENCES_TEXT = "text"

interface MainModel {
    fun loadData(): String?
    fun saveData(dataStr: String)
    fun deleteData()

    class Base(context: Context) : MainModel {

        private val pref = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        override fun loadData(): String? {
            return pref?.getString(APP_PREFERENCES_TEXT, "")
        }

        override fun saveData(dataStr: String) {
            val editor = pref.edit()
            editor.putString(APP_PREFERENCES_TEXT, dataStr)
            editor.apply()
        }

        override fun deleteData() {
            val editor = pref.edit()
            editor.clear()
            editor.apply()
        }
    }
}
