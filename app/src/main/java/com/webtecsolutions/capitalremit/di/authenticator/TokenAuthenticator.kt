package com.webtecsolutions.capitalremit.di.authenticator

import android.content.Context
import android.util.Log
import com.webtecsolutions.capitalremit.data.SharedPreferencesHelper
import com.webtecsolutions.capitalremit.data.TokenRefreshInterface
import com.webtecsolutions.capitalremit.helper.safeapicall.Resource
import com.webtecsolutions.capitalremit.helper.safeapicall.SafeApiCall
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject


private const val TAG = "AccessAuthenticator"

class AccessAuthenticator
@Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    @ApplicationContext
    private val appContext: Context,
    private val tokenRefreshApi: TokenRefreshInterface,
) : Authenticator {


    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            when (val newToken = getRefreshedToken(tokenRefreshApi)) {
                is Resource.Success -> {
                    Log.d(TAG, "authenticate: Token Refreshed")
                    sharedPreferencesHelper.accessToken = newToken.value.toString()
                    response.request.newBuilder()
                        .addHeader(
                            "Authorization",
                            newToken.value.toString()
                        ).build()
                }

                else -> {
                    Log.d(TAG, "authenticate: Token Refresh Failed")
                    startTokenExpiryHandlerActivity()
                    null
                }
            }
        }
    }

    private fun startTokenExpiryHandlerActivity() {

    }

    private suspend fun getRefreshedToken(tokenRefreshApi: TokenRefreshInterface): Resource<Any> {
        return SafeApiCall.execute { tokenRefreshApi.refreshToken() }
    }


}