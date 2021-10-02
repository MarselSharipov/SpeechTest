package ru.unluckybatman.speechtotexttest.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.unluckybatman.speechtotexttest.Theme
import java.io.IOException

class Utils(private val context: Context) {

    private fun getJsonMainDataFromAsset(): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open("main.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getThemeList(): List<Theme> {
        val jsonString = getJsonMainDataFromAsset()
        val listThemesType = object: TypeToken<List<Theme>>() {}.type
        return Gson().fromJson(jsonString, listThemesType)
    }

}