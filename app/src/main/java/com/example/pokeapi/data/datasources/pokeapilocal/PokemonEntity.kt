package com.example.pokeapi.data.datasources.pokeapilocal

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Class representing pokemon in database
 * @property position position of pokemon on remote PokeApi server.
 */
@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val position: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val firstType: String,
    val secondType: String?,
    val thirdType: String?,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val sprite: String?
)