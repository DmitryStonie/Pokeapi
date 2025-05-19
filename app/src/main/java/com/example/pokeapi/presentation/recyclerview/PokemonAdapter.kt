package com.example.pokeapi.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.pokeapi.R

/**
 * Adapter for PokemonItem elements.
 * @param diffCallback callback for PokemonItem elements.
 */
class PokemonAdapter(diffCallback: DiffUtil.ItemCallback<PokemonItem>) :
    PagingDataAdapter<PokemonItem, PokemonViewHolder>(diffCallback) {
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_recyclerview_element, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PokemonViewHolder, position: Int
    ) {
        val item = getItem(position)
        item?.let {
            holder.bind(item)
            holder.view.setOnClickListener { onClickListener?.onClick(position, item) }
        }
    }

    /**
     * Used to create on click callback from fragment with recycler view to handle element click.
     */
    fun interface OnClickListener {
        fun onClick(
            position: Int,
            item: PokemonItem,
        )
    }

    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }
}