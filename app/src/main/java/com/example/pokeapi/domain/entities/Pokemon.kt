package com.example.pokeapi.domain.entities


data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    val stats: List<PokemonStat>,
    val sprite: String
)