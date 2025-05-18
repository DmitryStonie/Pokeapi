package com.example.pokeapi.presentation.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapi.R
import com.squareup.picasso.Picasso

class PokemonAdapter(private val dataSet: ArrayList<PokemonItem>) :
    RecyclerView.Adapter<PokemonViewHolder>() {
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
        holder.textView.text = dataSet[position].name
        Picasso.get().load(dataSet[position].image).resize(400, 400)
            .into(holder.imageView)
        Log.d("INFO", "${holder.view.width}   ${holder.view.height}   ${holder.textView.width}")
        holder.view.setOnClickListener { onClickListener?.onClick(position, dataSet[position]) }
    }

    override fun getItemCount(): Int = dataSet.size

    interface OnClickListener {
        fun onClick(
            position: Int,
            item: PokemonItem,
        )
    }

    fun setData(newItems: List<PokemonItem>) {
        val diffUtilCallback = DiffUtilCallback(dataSet, newItems)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        dataSet.clear()
        dataSet.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }
}