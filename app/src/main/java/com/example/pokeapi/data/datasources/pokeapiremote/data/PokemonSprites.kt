package com.example.pokeapi.data.datasources.pokeapiremote.data

import com.google.gson.annotations.SerializedName

data class PokemonSprites(
    @SerializedName("front_default") val frontDefault: String
)