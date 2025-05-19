package com.example.pokeapi.data.datasources.pokeapilocal

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import javax.inject.Inject

class PokeApiLocalDatasource @Inject constructor(private val dao: PokemonDao) {
    suspend fun insertAll(users: List<PokemonEntity>){
        dao.insertAll(users)
    }

    fun pagingSource(): PagingSource<Int, PokemonEntity>{
        return dao.pagingSource()
    }

    suspend fun clearAll(){
        dao.clearAll()
    }

    suspend fun getPokemon(id: Int): PokemonEntity?{
        return dao.getPokemon(id)
    }
}