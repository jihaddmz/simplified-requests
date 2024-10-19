package com.jihaddmz.simplified_requests

import androidx.lifecycle.LifecycleCoroutineScope
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.wait
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object SimplifiedRequests {

    lateinit var service: ApiService
        private set

    fun setUpApi(
        baseUrl: String,
        useGsonConverter: Boolean = true,
        useMoshiConverter: Boolean = false,
        useScalarsConverter: Boolean = false,
        headers: HashMap<String, String>? = null
    ) {
        val client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val request = chain.request().newBuilder()

            request.addHeader("Accept", "application/json")
            request.addHeader("Content-Type", "application/json")

            headers?.forEach { (t, u) ->
                request.addHeader(
                    t,
                    u
                )
            }

            chain.proceed(request.build())
        }).addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val retrofit =
            Retrofit.Builder().baseUrl(baseUrl)
                .client(client)

        if (useGsonConverter)
            retrofit.addConverterFactory(GsonConverterFactory.create())

        if (useMoshiConverter)
            retrofit.addConverterFactory(MoshiConverterFactory.create())

        if (useScalarsConverter)
            retrofit.addConverterFactory(ScalarsConverterFactory.create())

        service = retrofit.build().create(ApiService::class.java)
    }

    inline fun <reified Res> callGet(
        endpoint: String,
        queryParams: HashMap<String, String>? = null,
        crossinline onSuccess: (Res) -> Unit,
        crossinline onFailed: (Exception) -> Unit = {}
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result: Res = if (queryParams != null) {
                    parseResponse(service.callGet(endpoint, queryParams).toString())
                } else {
                    parseResponse(service.callGet(endpoint).toString())

                }
                withContext(Dispatchers.Main) {
                    onSuccess(result)
                }
                cancel()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onFailed(e)
                }
                cancel()
            }
        }
    }

    suspend inline fun <reified Res : Any> callPost(
        endpoint: String,
        body: Any,
        onSuccess: (Res) -> Unit,
        onFailed: (Exception) -> Unit = {}
    ) {
        try {
            val result: Res = parseResponse(service.callPost(endpoint, body).toString())
            onSuccess(result)
        } catch (e: Exception) {
            onFailed(e)
        }
    }

    suspend inline fun <reified Res : Any> callPut(
        endpoint: String,
        body: Any,
        onSuccess: (Res) -> Unit,
        onFailed: (Exception) -> Unit = {}
    ) {
        try {
            val result: Res = parseResponse(service.callPut(endpoint, body).toString())
            onSuccess(result)
        } catch (e: Exception) {
            onFailed(e)
        }
    }

    suspend inline fun <reified Res : Any> callDelete(
        endpoint: String,
        onSuccess: (Res) -> Unit,
        onFailed: (Exception) -> Unit = {}
    ) {
        try {
            val result: Res = parseResponse(service.callDelete(endpoint).toString())
            onSuccess(result)
        } catch (e: Exception) {
            onFailed(e)
        }
    }

    // This function takes a response as a string and dynamically converts it to the desired type
    inline fun <reified T> parseResponse(responseBody: String): T {
        val type = object : TypeToken<T>() {}.type
        return GsonBuilder().setLenient().serializeNulls().serializeSpecialFloatingPointValues()
            .create().fromJson(responseBody, type)
    }
}