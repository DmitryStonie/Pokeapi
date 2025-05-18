package com.example.pokeapi.presentation.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapi.R
import com.squareup.picasso.Picasso

class PokemonAdapter(diffCallback: DiffUtil.ItemCallback<PokemonItem>) :
    PagingDataAdapter<PokemonItem, PokemonViewHolder>(diffCallback) {
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_recyclerview_element, parent, false)

        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PokemonViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.textView.text = item?.name
        Picasso.get().load(item?.image).error(R.mipmap.ic_launcher).resize(400, 400)
            .into(holder.imageView)
        holder.view.setOnClickListener { onClickListener?.onClick(position, item!!) }
    }

    interface OnClickListener {
        fun onClick(
            position: Int,
            item: PokemonItem,
        )
    }

    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }
}