package com.example.pokeapi.data.datasources.pokeapilocal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.paging.PagingSource

/**
 * Dao for accessing pokemon from database.
 */
@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon")
    suspend fun getAll(): List<PokemonEntity>?

    @Query("SELECT * FROM pokemon")
    fun pagingSource(): PagingSource<Int, PokemonEntity>

    @Query("DELETE FROM pokemon")
    suspend fun clearAll()

    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemon(id: Int): PokemonEntity?

    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun getNumberOfPokemon(): Int

    @Query("SELECT min(position) FROM pokemon")
    suspend fun getMinimalPokemonPosition(): Int?

    @Query("SELECT max(position) FROM pokemon")
    suspend fun getMaximalPokemonPosition(): Int?
}