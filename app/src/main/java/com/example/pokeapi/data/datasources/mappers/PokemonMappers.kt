package com.example.pokeapi.data.datasources.mappers

import com.example.pokeapi.data.datasources.pokeapilocal.PokemonEntity
import com.example.pokeapi.data.datasources.pokeapiremote.data.PokemonInfo
import com.example.pokeapi.data.datasources.pokeapiremote.data.PokemonStat
import com.example.pokeapi.domain.entities.Pokemon

fun PokemonInfo.toPokemonEntity(): PokemonEntity{
    var hp = 0
    var attack = 0
    var defense = 0
    var specialAttack = 0
    var specialDefense = 0
    var speed = 0
    for (stat in this.stats) {
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
        this.id,
        this.name,
        this.height,
        this.weight,
        this.types.getOrNull(0)?.type?.name ?: "",
        this.types.getOrNull(1)?.type?.name ?: "",
        this.types.getOrNull(2)?.type?.name ?: "",
        hp,
        attack,
        defense,
        specialAttack,
        specialDefense,
        speed,
        this.sprites.frontDefault
    )
}

fun PokemonInfo.toPokemon(): Pokemon{
    var hp = 0
    var attack = 0
    var defense = 0
    var specialAttack = 0
    var specialDefense = 0
    var speed = 0
    for (stat in this.stats) {
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

    return Pokemon(
        this.id,
        this.name,
        this.height,
        this.weight,
        this.types.map { type -> type.type.name },
        hp,
        attack,
        defense,
        specialAttack,
        specialDefense,
        speed,
        this.sprites.frontDefault
    )
}

fun PokemonEntity.toPokemon(): Pokemon{
    val types = ArrayList<String>()
    types.add(this.firstType)
    secondType?.let{ types.add(it)}
    thirdType?.let{ types.add(it)}
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