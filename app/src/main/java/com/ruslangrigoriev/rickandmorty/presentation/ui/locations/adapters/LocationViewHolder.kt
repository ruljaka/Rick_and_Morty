package com.ruslangrigoriev.rickandmorty.presentation.ui.locations.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.locations.Location
import com.ruslangrigoriev.rickandmorty.databinding.ItemLocationBinding

class LocationViewHolder(
    private val view: View,
    private val onItemClicked: (id: Int) -> Unit,
) : RecyclerView.ViewHolder(view) {

    fun bind(location: Location) {
        val binding = ItemLocationBinding.bind(view)

        with(binding) {
            root.setOnClickListener {
                onItemClicked(location.id)
            }
            locationNameTextView.text = location.name
            locationTypeTextView.text = location.type
            locationDimensionTextView.text = location.dimension
        }
    }
}