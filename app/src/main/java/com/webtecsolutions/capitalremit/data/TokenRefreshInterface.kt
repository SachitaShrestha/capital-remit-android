package com.webtecsolutions.capitalremit.data

import com.webtecsolutions.capitalremit.data.constants.ApiEndPoints
import retrofit2.Response
import retrofit2.http.POST

interface TokenRefreshInterface {

    @POST(ApiEndPoints.REFRESH_TOKEN)
    suspend fun refreshToken(): Response<Any>
}