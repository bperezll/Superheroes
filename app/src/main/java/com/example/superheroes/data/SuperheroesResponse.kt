package com.example.superheroes.data

import com.google.gson.annotations.SerializedName

class SuperheroesResponse(
    @SerializedName("response") val response:String,
    @SerializedName("results-for") val resultsFor:String,
    @SerializedName("results") val results:List<Superhero>
    )

class Superhero (
    @SerializedName("id") val id:String,
    @SerializedName("name") val name:String,
    @SerializedName("biography") val biography:Biography, // Add biography of type Biography to get access by 'superhero.biography.firstAppearance'
    @SerializedName("image") val image:Image
)

class Image (
    @SerializedName("url") val url:String
)

class Biography (
    @SerializedName("first-appearance") val firstAppearance:String,
    @SerializedName("publisher") val publisher:String
)