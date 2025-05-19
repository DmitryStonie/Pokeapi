package com.example.pokeapi.presentation.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.pokeapi.R
import com.squareup.picasso.MemoryPolicy
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
        if(item?.image != null){
            Picasso.get().load(item.image).resize(400, 400)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.imageView)
        }
        holder.attackIconView.isVisible = item?.isMaxAttack == true
        holder.defenceIconView.isVisible = item?.isMaxDefence == true
        holder.hpIconView.isVisible = item?.isMaxHp == true
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