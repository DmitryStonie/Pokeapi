package com.example.pokeapi.data.datasources.pokeapiremote.data

import com.google.gson.annotations.SerializedName

/**
 * Dto class for saving pokemon front image url.
 */
data class PokemonSprites(
    @SerializedName("front_default") val frontDefault: String
)