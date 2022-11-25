package com.kerimbr.crypto_kotlin.service

import com.kerimbr.crypto_kotlin.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    @GET("kerimbr/My-Fake-JSON-Placeholder/main/cryptocurrency.json")
    fun getCryptoData(): Observable<List<CryptoModel>>
//fun getCryptoData(): Call<List<CryptoModel>>

}