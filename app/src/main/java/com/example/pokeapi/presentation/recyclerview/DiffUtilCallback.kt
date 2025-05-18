package com.example.pokeapi.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(private val oldItems: List<PokemonItem>, private val newItems: List<PokemonItem>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return true
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldItems[oldItemPosition].name == newItems[newItemPosition].name &&
                oldItems[oldItemPosition].image == newItems[newItemPosition].image
    }
}