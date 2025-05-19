package com.example.pokeapi.ui

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapi.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import com.example.pokeapi.R
import com.example.pokeapi.presentation.recyclerview.PokemonAdapter
import com.example.pokeapi.presentation.recyclerview.PokemonComparator
import com.example.pokeapi.presentation.recyclerview.PokemonItem
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class MainScreenFragment : Fragment(R.layout.fragment_recyclerview_screen) {

    val viewModel: MainViewModel by activityViewModels<MainViewModel>()
    val recyclerAdapter: PokemonAdapter = PokemonAdapter(PokemonComparator)

    lateinit var recyclerView: RecyclerView
    lateinit var topAppBar: MaterialToolbar
    lateinit var attackCheckBox: CheckBox
    lateinit var defenseCheckBox: CheckBox
    lateinit var hpCheckBox: CheckBox

    lateinit var currentFlowJob: Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        topAppBar = view.findViewById<MaterialToolbar>(R.id.topAppBar)
        attackCheckBox = view.findViewById<CheckBox>(R.id.attackCheckbox)
        defenseCheckBox = view.findViewById<CheckBox>(R.id.defenseCheckbox)
        hpCheckBox = view.findViewById<CheckBox>(R.id.hpCheckbox)

        recyclerView.layoutManager = GridLayoutManager(context, NUM_OF_COLUMNS)
        recyclerAdapter.setOnClickListener(object : PokemonAdapter.OnClickListener {
            override fun onClick(
                position: Int, item: PokemonItem
            ) {
                viewModel.selectPokemon(item.id)
                parentFragmentManager.beginTransaction().setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out,
                ).add(R.id.fragment_container_view, PokemonFragment()).addToBackStack(
                    PokemonFragment.POKEMON_FRAGMENT
                ).commit()
            }

        })
        recyclerView.adapter = recyclerAdapter
        viewModel.initPagerFlow()
        observeViewModelFlow()
        topAppBar.setNavigationOnClickListener {
            resetCheckboxes()
            currentFlowJob.cancel()
            viewModel.resetSelectedPokemon()
            viewModel.initPagerFlow(Random.nextInt(0, NUM_OF_POKEMON))
            observeViewModelFlow()
        }
        attackCheckBox.setOnCheckedChangeListener { _, _ ->
            sortClicked()
        }
        defenseCheckBox.setOnCheckedChangeListener { _, _ ->
            sortClicked()
        }
        hpCheckBox.setOnCheckedChangeListener { _, _ ->
            sortClicked()
        }
    }

    private fun observeViewModelFlow() {
        currentFlowJob = lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                recyclerAdapter.submitData(pagingData)
            }
        }
    }

    private fun sortClicked() {
        currentFlowJob.cancel()
        if (!attackCheckBox.isChecked && !hpCheckBox.isChecked && !defenseCheckBox.isChecked) {
            viewModel.resetSelectedPokemon()
            viewModel.initPagerFlow(Random.nextInt(0, NUM_OF_POKEMON))
            observeViewModelFlow()
        } else {
            viewModel.sortPokemon(
                attackCheckBox.isChecked, hpCheckBox.isChecked, defenseCheckBox.isChecked
            ).observe(viewLifecycleOwner) { pokemon ->
                currentFlowJob = lifecycleScope.launch {
                    recyclerAdapter.submitData(PagingData.from(pokemon))
                    recyclerView.scrollToPosition(0)
                }
            }
        }
    }

    private fun resetCheckboxes() {
        attackCheckBox.isChecked = false
        defenseCheckBox.isChecked = false
        hpCheckBox.isChecked = false
    }

    companion object {
        const val NUM_OF_COLUMNS = 3
        const val NUM_OF_POKEMON = 1302
    }

}