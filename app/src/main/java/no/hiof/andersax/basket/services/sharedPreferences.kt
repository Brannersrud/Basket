package no.hiof.andersax.basket.services

import android.content.Context
import android.content.SharedPreferences

class sharedPreferencesManipulator {

    fun savePrefsData(context: Context, key:String, location: String){
        val preferences : SharedPreferences = context.getSharedPreferences(location, Context.MODE_PRIVATE)
        val prefEd : SharedPreferences.Editor = preferences.edit()

        prefEd.putBoolean(key, true)
        prefEd.apply()
    }

    fun restorePrefData(context : Context, key : String, location : String) : Boolean{
        val pref : SharedPreferences = context.getSharedPreferences(location, Context.MODE_PRIVATE)
        val isIntroFinished = pref.getBoolean(key, false)

        return isIntroFinished
    }

}