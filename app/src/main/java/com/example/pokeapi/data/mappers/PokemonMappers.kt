package com.example.pokeapi.data.mappers

import com.example.pokeapi.data.datasources.pokeapilocal.PokemonEntity
import com.example.pokeapi.data.datasources.pokeapiremote.data.PokemonDto
import com.example.pokeapi.domain.entities.Pokemon

fun PokemonDto.toPokemonEntity(): PokemonEntity {
    var hp = 0
    var attack = 0
    var defense = 0
    var specialAttack = 0
    var specialDefense = 0
    var speed = 0
    for (stat in this.pokemonInfo.stats) {
        when (stat.stat.name) {
            "hp" -> hp = stat.baseStat
            "attack" -> attack = stat.baseStat
            "defense" -> defense = stat.baseStat
            "special-attack" -> specialAttack = stat.baseStat
            "special-defense" -> specialDefense = stat.baseStat
            "speed" -> speed = stat.baseStat
            else -> {}
        }
    }
    return PokemonEntity(
        this.pokemonInfo.id,
        this.order,
        this.pokemonInfo.name,
        this.pokemonInfo.height,
        this.pokemonInfo.weight,
        this.pokemonInfo.types.getOrNull(0)?.type?.name ?: "",
        this.pokemonInfo.types.getOrNull(1)?.type?.name,
        this.pokemonInfo.types.getOrNull(2)?.type?.name,
        hp,
        attack,
        defense,
        specialAttack,
        specialDefense,
        speed,
        this.pokemonInfo.sprites.frontDefault
    )
}

fun PokemonEntity.toPokemon(): Pokemon {
    val types = ArrayList<String>()
    types.add(this.firstType)
    secondType?.let { types.add(it) }
    thirdType?.let { types.add(it) }
    return Pokemon(
        this.id,
        this.name,
        this.height,
        this.weight,
        types,
        hp,
        attack,
        defense,
        specialAttack,
        specialDefense,
        speed,
        this.sprite
    )
}