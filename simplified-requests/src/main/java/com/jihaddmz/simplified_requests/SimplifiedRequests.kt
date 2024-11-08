package com.jihaddmz.simplified_requests

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object SimplifiedRequests {

    lateinit var service: ApiService
        private set

    /**
     * Setting up the api to be able to call the http requests. This method should be called before any request call.
     * @param baseUrl this is the base url of the backend api. Note: don't append / to it
     * @param useGsonConverter true if you want to use GsonConverter with the api
     * @param useMoshiConverter true if you want to use MoshiConverter with the api
     * @param useScalarsConverter true if you want to use ScalarsConverter with the api
     * @param headers any important headers you want to add. Like token header
     **/
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

    /**
     * Calling a get http request using the baseUrl and the endpoint provided here.
     * @param endpoint the endpoint appended to the baseUrl you wish to call. Note: don't prefix the endpoint with /
     * @param queryParams the parameters added to the url
     * @param headers any other headers specifically designed to this api call. Note: if you have passed the same
     * header as above, the latter will be overridden by this one
     * @param onSuccess a callback for when the result is returned
     * @param onFailed a callback for when an error has returned
     **/
    suspend inline fun <reified Res> callGet(
        endpoint: String,
        queryParams: HashMap<String, String>? = null,
        headers: Map<String, String> = mapOf(),
        crossinline onSuccess: (Res) -> Unit,
        crossinline onFailed: (String) -> Unit = {}
    ) {
        try {
            val result: Res = if (queryParams != null) {
                parseResponse(service.callGet(endpoint, queryParams, headers).toString())
            } else {
                parseResponse(service.callGet(endpoint).toString())

            }
            onSuccess(result)
        } catch (e: HttpException) {
            onFailed(e.response()?.errorBody()?.string() ?: "Unknown Exception")
        }
    }

    /**
     * Calling a post http request using the baseUrl and the endpoint provided here.
     * @param endpoint the endpoint appended to the baseUrl you wish to call. Note: don't prefix the endpoint with /
     * @param body the request body attached to this request. This could be either as simple as a simple String, or
     * as a complex object, like a self created data class.
     * @param headers any other headers specifically designed to this api call. Note: if you have passed the same
     * header as above, the latter will be overridden by this one
     * @param onSuccess a callback for when the result is returned
     * @param onFailed a callback for when an error has returned
     **/
    suspend inline fun <reified Res : Any> callPost(
        endpoint: String,
        body: Any,
        headers: Map<String, String> = mapOf(),
        crossinline onSuccess: (Res) -> Unit,
        crossinline onFailed: (String) -> Unit = {}
    ) {
        try {
            val result: Res =
                parseResponse(service.callPost(endpoint, body, headers).toString())
            onSuccess(result)
        } catch (e: HttpException) {
            onFailed(e.response()?.errorBody()?.string() ?: "Unknown Exception")
        }
    }

    /**
     * Calling a put http request using the baseUrl and the endpoint provided here.
     * @param endpoint the endpoint appended to the baseUrl you wish to call. Note: don't prefix the endpoint with /
     * @param body the request body attached to this request. This could be either as simple as a simple String, or
     * as a complex object, like a self created data class.
     * @param headers any other headers specifically designed to this api call. Note: if you have passed the same
     * header as above, the latter will be overridden by this one
     * @param onSuccess a callback for when the result is returned
     * @param onFailed a callback for when an error has returned
     **/
    suspend inline fun <reified Res : Any> callPut(
        endpoint: String,
        body: Any,
        headers: Map<String, String> = mapOf(),
        crossinline onSuccess: (Res) -> Unit,
        crossinline onFailed: (String) -> Unit = {}
    ) {
        try {
            val result: Res = parseResponse(service.callPut(endpoint, body, headers).toString())
            onSuccess(result)
        } catch (e: HttpException) {
            onFailed(e.response()?.errorBody()?.string() ?: "Unknown Exception")
        }
    }

    /**
     * Calling a delete http request using the baseUrl and the endpoint provided here.
     * @param endpoint the endpoint appended to the baseUrl you wish to call. Note: don't prefix the endpoint with /
     * @param headers any other headers specifically designed to this api call. Note: if you have passed the same
     * header as above, the latter will be overridden by this one
     * @param onSuccess a callback for when the result is returned
     * @param onFailed a callback for when an error has returned
     **/
    suspend inline fun <reified Res : Any> callDelete(
        endpoint: String,
        headers: Map<String, String> = mapOf(),
        crossinline onSuccess: (Res) -> Unit,
        crossinline onFailed: (String) -> Unit = {}
    ) {
        try {
            val result: Res = parseResponse(service.callDelete(endpoint, headers).toString())
            onSuccess(result)
        } catch (e: HttpException) {
            onFailed(e.response()?.errorBody()?.string() ?: "Unknown Exception")
        }
    }

    // This function takes a response as a string and dynamically converts it to the desired type
    inline fun <reified T> parseResponse(responseBody: String): T {
        val type = object : TypeToken<T>() {}.type
        return GsonBuilder().setLenient().serializeNulls().serializeSpecialFloatingPointValues()
            .create().fromJson(responseBody, type)
    }
}