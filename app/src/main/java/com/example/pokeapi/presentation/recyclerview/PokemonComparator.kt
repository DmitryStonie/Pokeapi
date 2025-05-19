package com.example.pokeapi.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil

object PokemonComparator : DiffUtil.ItemCallback<PokemonItem>() {
    override fun areItemsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
        return oldItem == newItem
    }
}