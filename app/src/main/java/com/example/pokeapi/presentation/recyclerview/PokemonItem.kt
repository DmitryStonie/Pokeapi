package com.example.pokeapi.presentation.recyclerview

/**
 * Data class for storing items in RecyclerView.
 * @property imageUrl url of front sprite of pokemon.
 * @property isMaxAttack is true if on sorting mode this pokemon has biggest attack.
 * @property isMaxDefense is true if on sorting mode this pokemon has biggest defense.
 * @property isMaxHp is true if on sorting mode this pokemon has biggest hp.
 */
data class PokemonItem(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val isMaxAttack: Boolean,
    val isMaxDefense: Boolean,
    val isMaxHp: Boolean
)