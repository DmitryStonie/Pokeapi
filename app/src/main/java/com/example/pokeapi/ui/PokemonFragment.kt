package com.example.pokeapi.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pokeapi.R
import com.example.pokeapi.presentation.MainViewModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
/**
 * Fragment with pokemon image and stats.
 */
class PokemonFragment : Fragment(R.layout.fragment_pokemon_info) {

    val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    lateinit var imageView: ImageView
    lateinit var nameView: TextView
    lateinit var hpView: TextView
    lateinit var heightView: TextView
    lateinit var weightView: TextView
    lateinit var typesView: TextView
    lateinit var attackView: TextView
    lateinit var defenseView: TextView
    lateinit var speedView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById<ImageView>(R.id.image)
        nameView = view.findViewById<TextView>(R.id.name)
        hpView = view.findViewById<TextView>(R.id.hp)
        heightView = view.findViewById<TextView>(R.id.height)
        weightView = view.findViewById<TextView>(R.id.weight)
        typesView = view.findViewById<TextView>(R.id.types)
        attackView = view.findViewById<TextView>(R.id.attack)
        defenseView = view.findViewById<TextView>(R.id.defense)
        speedView = view.findViewById<TextView>(R.id.speed)
        viewModel.selectedPokemon.observe(viewLifecycleOwner) { pokemon ->
            if (pokemon != null) {
                Picasso.get().load(pokemon.spriteUrl).memoryPolicy(MemoryPolicy.NO_CACHE).resize(600, 600).into(imageView)
                nameView.text = pokemon.name
                hpView.text = pokemon.hp.toString()
                heightView.text = pokemon.height.toString()
                weightView.text = pokemon.weight.toString()
                typesView.text = pokemon.types.joinToString(", ", "")
                attackView.text = String.format(
                    resources.getString(R.string.stat_string), pokemon.attack, pokemon.specialAttack
                )
                defenseView.text = String.format(
                    resources.getString(R.string.stat_string),
                    pokemon.defense,
                    pokemon.specialDefense
                )
                speedView.text = pokemon.speed.toString()

            }
        }
    }

    companion object {
        const val POKEMON_FRAGMENT = "PokemonFragment"
    }
}