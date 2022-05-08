package com.ruslangrigoriev.rickandmorty.presentation.locations.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.rickandmorty.data.dto.locationDTO.LocationDTO
import com.ruslangrigoriev.rickandmorty.databinding.ItemLocationBinding

class LocationViewHolder(
    private val view: View,
    private val onItemClicked: (id: Int) -> Unit,
) : RecyclerView.ViewHolder(view) {

    fun bind(location: LocationDTO) {
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