package com.ciphra.android.thecattery

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface GetRandomCat {
    @Headers("x-api-key: 4315f139-b46e-40fd-b195-b8a108ac0cf5")
    @GET("search")
    fun getCats(): Call<MutableList<Cat>>
}