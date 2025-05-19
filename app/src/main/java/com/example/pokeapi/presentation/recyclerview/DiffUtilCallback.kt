package com.example.pokeapi.presentation.recyclerview

import android.util.Log
import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(private val oldItems: List<PokemonItem>, private val newItems: List<PokemonItem>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        Log.d("INFO", "areItemsTheSame ${oldItems[oldItemPosition].id}   ${newItems[newItemPosition].id}")
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        Log.d("INFO", "areContentsTheSame ${oldItems[oldItemPosition]}   ${newItems[newItemPosition]}")
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}