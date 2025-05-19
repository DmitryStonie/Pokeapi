package com.example.pokeapi.data.datasources.pokeapiremote.data

import com.google.gson.annotations.SerializedName

/**
 * Dto class for deserializing PokeApi response, stat object.
 */
data class PokemonStat(
    val stat: NamedApiResource,
    val effort: Int,
    @SerializedName(value = "base_stat") val baseStat: Int
)