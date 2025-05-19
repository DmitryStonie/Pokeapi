package com.example.pokeapi.presentation.recyclerview

import android.util.Log
import androidx.recyclerview.widget.DiffUtil

object PokemonComparator : DiffUtil.ItemCallback<PokemonItem>() {
    override fun areItemsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
//        Log.d("INFO", "areItemsTheSame ${oldItem.id}   ${newItem.id}  ${oldItem.id == newItem.id}")
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
//        Log.d("INFO", "areContentsTheSame  ${oldItem.id}  ${oldItem == newItem}")

        return oldItem == newItem
    }
}