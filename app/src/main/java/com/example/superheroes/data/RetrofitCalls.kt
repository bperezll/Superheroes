package com.example.superheroes.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCalls {
    private val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl("https://superheroapi.com/api/7252591128153666/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: SuperheroesServiceApi = retrofit.create(SuperheroesServiceApi::class.java)
}