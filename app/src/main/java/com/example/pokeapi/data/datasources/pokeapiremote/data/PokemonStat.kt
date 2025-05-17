package com.example.pokeapi.data.datasources.pokeapiremote.data

import com.google.gson.annotations.SerializedName

data class PokemonStat(
    val stat: NamedApiResource,
    val effort: Int,
    @SerializedName(value="base_stat")
    val baseStat: Int
)