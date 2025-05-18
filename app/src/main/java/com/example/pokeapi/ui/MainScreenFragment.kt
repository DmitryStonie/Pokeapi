package com.example.pokeapi.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapi.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import com.example.pokeapi.R
import com.example.pokeapi.presentation.recyclerview.DiffUtilCallback
import com.example.pokeapi.presentation.recyclerview.PokemonAdapter
import com.example.pokeapi.presentation.recyclerview.PokemonComparator
import com.example.pokeapi.presentation.recyclerview.PokemonItem
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainScreenFragment : Fragment(R.layout.fragment_recyclerview_screen) {

    val viewModel: MainViewModel by activityViewModels<MainViewModel>()
    val recyclerAdapter: PokemonAdapter = PokemonAdapter(PokemonComparator)

    lateinit var recyclerView: RecyclerView
    lateinit var topAppBar: MaterialToolbar
    lateinit var attackCheckBox: CheckBox
    lateinit var defenseCheckBox: CheckBox
    lateinit var hpCheckBox: CheckBox

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPokemonList()
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        topAppBar = view.findViewById<MaterialToolbar>(R.id.topAppBar)
        attackCheckBox = view.findViewById<CheckBox>(R.id.attackCheckbox)
        defenseCheckBox = view.findViewById<CheckBox>(R.id.defenseCheckbox)
        hpCheckBox = view.findViewById<CheckBox>(R.id.hpCheckbox)

        recyclerAdapter.setOnClickListener(object : PokemonAdapter.OnClickListener {
            override fun onClick(
                position: Int,
                item: PokemonItem
            ) {
                Log.d("INFO", "${item}")
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out,
                    )
                    .add(R.id.fragment_container_view, PokemonFragment()).addToBackStack(
                        PokemonFragment.POKEMON_FRAGMENT
                    )
                    .commit()
            }

        })
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = recyclerAdapter

        topAppBar.setNavigationOnClickListener {
            viewModel.resetSelectedPokemon()
            viewModel.getPokemonList()
        }
        attackCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("INFO", "$isChecked")
        }
        defenseCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("INFO", "$isChecked")
        }
        hpCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("INFO", "$isChecked")
        }

        viewModel.pokemonState.observe(viewLifecycleOwner) {
            val items = it.map { pokemon -> PokemonItem(pokemon.id, pokemon.name, pokemon.sprite) }
//            recyclerAdapter.setData(items)
        }
        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                val items = pagingData.map { pokemon ->
                    PokemonItem(pokemon.id, pokemon.name, pokemon.sprite) }
                recyclerAdapter.submitData(items)
            }
        }

    }

    companion object {
        const val MAIN_SCREEN_FRAGMENT = "MainScreenFragment"
    }

}