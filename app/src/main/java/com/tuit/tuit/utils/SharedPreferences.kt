package com.tuit.tuit.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri

object SharedPreferences {


    fun saveImageUrl(context: Context, url: String) {
        val sharedPreferences = context.getSharedPreferences("Elon", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("saveImageUrl", url).apply()
    }

    fun getImageUrl(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("Elon", Context.MODE_PRIVATE)
        return sharedPreferences.getString("saveImageUrl", "")
    }



    fun setLoggedIn(context: Context, lang: Boolean) {
        val sharedPreferences = context.getSharedPreferences("Elon", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("login", lang).apply()
    }

    fun isLoggedIn(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences("Elon", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("login", false)
    }


    fun saveIsTeacher(context: Context, lang: Boolean) {
        val sharedPreferences = context.getSharedPreferences("Elon", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("teacher", lang).apply()
    }

    fun getGetIsTeacher(context: Context): Boolean? {
        val sharedPreferences = context.getSharedPreferences("Elon", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("teacher", false)
    }

    fun saveToken(context: Context, lang: String) {
        val sharedPreferences = context.getSharedPreferences("Elon", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("token", lang).apply()
    }

    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("Elon", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", "")
    }


    fun saveExpireDate(context: Context, lang: Long) {
        val sharedPreferences = context.getSharedPreferences("Elon", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putLong("expired_at", lang).apply()
    }

    fun getExpireDate(context: Context): Long? {
        val sharedPreferences = context.getSharedPreferences("Elon", Context.MODE_PRIVATE)
        return sharedPreferences.getLong("expired_at", 0)
    }


    fun savePhone(context: Context, lang: String) {
        val sharedPreferences = context.getSharedPreferences("Elon", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("phone_number", lang).apply()
    }

    fun getPhone(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("Elon", Context.MODE_PRIVATE)
        return sharedPreferences.getString("phone_number", "")
    }

    fun getProfileImage(context: Context, fileUri:Uri):String?{
        val sharedPreferences = context.getSharedPreferences("Image", Context.MODE_PRIVATE)
        return sharedPreferences.getString("fileUrl", fileUri.toString())
    }

}