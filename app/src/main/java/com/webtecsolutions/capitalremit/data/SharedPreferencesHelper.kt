package com.webtecsolutions.capitalremit.data


import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.webtecsolutions.capitalremit.data.constants.Config.PREF_NAME
import com.webtecsolutions.capitalremit.data.constants.AppData.ACCESS_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("CommitPrefEdits")
@Singleton
class SharedPreferencesHelper
@Inject
constructor(@ApplicationContext context: Context) {
    private val sharedPref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    var accessToken: String?
        get() = sharedPref.getString(ACCESS_TOKEN, "")
        set(accessToken) = sharedPref.edit().putString(ACCESS_TOKEN, accessToken).apply()


}
