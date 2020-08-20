package com.ciphra.android.thecattery.ui.main

import androidx.lifecycle.ViewModel
import com.ciphra.android.thecattery.Cat
import com.ciphra.android.thecattery.GetRandomCat
import com.ciphra.android.thecattery.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainViewModel : ViewModel() {
    var thisCat = SingleLiveEvent<Cat>()
    var failure = SingleLiveEvent<Boolean>()
    var isProcessingCat = false
    fun retrieveOneRandomCat(){
        isProcessingCat = true
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/images/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val randomApi = retrofit.create(GetRandomCat::class.java)
        val call = randomApi.getCats()
        call.enqueue(object : Callback<MutableList<Cat>>{
            override fun onResponse(call: Call<MutableList<Cat>>, response: Response<MutableList<Cat>>) {
                if (response.isSuccessful()) {
                    val body = response.body()
                    if(body.isNullOrEmpty()){
                        retrieveOneRandomCat()
                    }
                    else{
                        thisCat.postValue(body.first())
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<Cat>>, t: Throwable) {
                failure.postValue(true)
            }
        })
    }
}