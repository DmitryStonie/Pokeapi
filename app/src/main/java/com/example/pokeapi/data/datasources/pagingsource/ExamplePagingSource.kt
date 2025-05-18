package com.example.pokeapi.data.datasources.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokeapi.data.repositories.PokeApiRemoteRepositoryImpl
import com.example.pokeapi.domain.entities.Pokemon
import javax.inject.Inject

class ExamplePagingSource @Inject constructor(
    val pokeApiRemoteRepository: PokeApiRemoteRepositoryImpl
) : PagingSource<Int, Pokemon>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Pokemon> {
        try {
            val nextPageNumber = params.key ?: 0
            val pokemonList = pokeApiRemoteRepository.getPokemonList(params.loadSize, nextPageNumber)
            return LoadResult.Page(
                data = pokemonList!!,
                prevKey = null,
                nextKey = nextPageNumber + params.loadSize
            )
        } catch (e: Exception) {
            Log.d("ERROR", "${e.message}")
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}