package com.example.pokeapi.presentation.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapi.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

/**
 * ViewHolder for pokemon in RecyclerView.
 * @param view it's view
 */
class PokemonViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.findViewById(R.id.image)
    val textView: TextView = view.findViewById(R.id.name)
    val attackIconView: ImageView = view.findViewById(R.id.attackIcon)
    val defenceIconView: ImageView = view.findViewById(R.id.defenseIcon)
    val hpIconView: ImageView = view.findViewById(R.id.hpIcon)

    fun bind(item: PokemonItem){
        textView.text = item.name
        if (item.imageUrl != null) {
            Picasso.get().load(item.imageUrl).resize(400, 400).memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imageView)
        }
        attackIconView.isVisible = item.isMaxAttack == true
        defenceIconView.isVisible = item.isMaxDefense == true
        hpIconView.isVisible = item.isMaxHp == true
    }
}