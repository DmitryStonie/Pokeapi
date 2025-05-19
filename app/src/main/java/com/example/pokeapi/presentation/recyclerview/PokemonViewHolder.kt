package com.example.pokeapi.presentation.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapi.R

class PokemonViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.findViewById(R.id.image)
    val textView: TextView = view.findViewById(R.id.name)
    val attackIconView: ImageView = view.findViewById(R.id.attackIcon)
    val defenceIconView: ImageView = view.findViewById(R.id.defenseIcon)
    val hpIconView: ImageView = view.findViewById(R.id.hpIcon)
}