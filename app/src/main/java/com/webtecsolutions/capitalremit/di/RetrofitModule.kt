package com.webtecsolutions.capitalremit.di


import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.webtecsolutions.capitalremit.data.ApiInterface
import com.webtecsolutions.capitalremit.data.SharedPreferencesHelper
import com.webtecsolutions.capitalremit.data.TokenRefreshInterface
import com.webtecsolutions.capitalremit.data.constants.Config
import com.webtecsolutions.capitalremit.di.authenticator.AccessAuthenticator
import com.webtecsolutions.capitalremit.helper.annotation.SkipSerialisation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


private const val TAG = "RetrofitModule"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    private fun getHttpClient(
        sharedPreferencesHelper: SharedPreferencesHelper,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        appContext: Context,
        authenticator: AccessAuthenticator? = null
    ): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
        httpBuilder.addInterceptor(httpLoggingInterceptor)
        httpBuilder.addInterceptor(
            getInterceptorWithTokenHeader(
                sharedPreferencesHelper,
                appContext
            )
        )
        authenticator?.let { httpBuilder.authenticator(authenticator) }
        return httpBuilder.build()
    }


    @Singleton
    @Provides
    fun providesRetrofit(
        @ApplicationContext appContext: Context,
        sharedPreferencesHelper: SharedPreferencesHelper,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authenticator: AccessAuthenticator,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Config.BASE_URL)
            .client(
                getHttpClient(
                    sharedPreferencesHelper,
                    httpLoggingInterceptor,
                    appContext,
                    authenticator
                )
            )
            .build()


    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().addSerializationExclusionStrategy(object : ExclusionStrategy {

            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return f.getAnnotation(SkipSerialisation::class.java) != null
            }

            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                return false
            }
        }).create()
    }


    @SuppressLint("HardwareIds")
    private fun getInterceptorWithTokenHeader(
        sharedPreferencesHelper: SharedPreferencesHelper,
        appContext: Context
    ): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Authorization", "Bearer " + sharedPreferencesHelper.accessToken)
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }

    }

    @Singleton
    @Provides
    fun providesMainApi(retrofit: Retrofit): ApiInterface =
        retrofit.create(
            ApiInterface::class.java
        )


    @Singleton
    @Provides
    fun providesTokenRefreshApi(
        sharedPreferencesHelper: SharedPreferencesHelper,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @ApplicationContext appContext: Context
    ): TokenRefreshInterface =
        Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient(sharedPreferencesHelper, httpLoggingInterceptor, appContext))
            .build()
            .create(TokenRefreshInterface::class.java)

}