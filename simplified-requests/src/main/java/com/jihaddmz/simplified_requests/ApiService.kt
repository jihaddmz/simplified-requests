package com.jihaddmz.simplified_requests

import com.google.gson.JsonElement
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    @GET("{endpoint}")
    suspend fun callGet(
        @Path("endpoint", encoded = true) endpoint: String,
        @HeaderMap headers: Map<String, String> = mapOf()
    ): JsonElement


    @GET("{endpoint}")
    suspend fun callGet(
        @Path("endpoint", encoded = true) endpoint: String,
        @QueryMap options: Map<String, String>,
        @HeaderMap headers: Map<String, String> = mapOf()
    ): JsonElement

    @POST("{endpoint}")
     suspend fun callPost(
        @Path("endpoint", encoded = true) endpoint: String,
        @Body body: Any,
        @HeaderMap headers: Map<String, String> = mapOf()
    ): JsonElement

    @POST("{endpoint}")
    suspend fun callPost(
        @Path("endpoint", encoded = true) endpoint: String,
        @QueryMap options: Map<String, String>,
        @HeaderMap headers: Map<String, String> = mapOf()
    ): JsonElement

     @PUT("{endpoint}")
     suspend fun callPut(
         @Path("endpoint", encoded = true) endpoint: String,
         @Body body: Any,
         @HeaderMap headers: Map<String, String> = mapOf()
     ): JsonElement

     @DELETE("{endpoint}")
     suspend fun callDelete(
         @Path("endpoint", encoded = true) endpoint: String,
         @HeaderMap headers: Map<String, String> = mapOf()
     ): JsonElement
}