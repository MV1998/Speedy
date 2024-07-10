package com.example.speedy.database

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("StaticFieldLeak")
object LocalPairedDB {
    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences

    val todos : MutableList<String> = mutableListOf()

    fun initialize(context: Context) {
        this.context = context
       sharedPreferences =  context.getSharedPreferences("speedy", Context.MODE_PRIVATE)
    }

    fun saveData(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getData(key: String) : String? {
      return sharedPreferences.getString(key, null)
    }
}