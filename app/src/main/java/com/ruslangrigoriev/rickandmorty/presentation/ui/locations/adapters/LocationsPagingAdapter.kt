package com.ruslangrigoriev.rickandmorty.presentation.ui.locations.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.data.dto.locationDTO.LocationDTO

class LocationsPagingAdapter(
    private val onItemClicked: (id: Int) -> Unit,
) : PagingDataAdapter<LocationDTO, LocationViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<LocationDTO>() {
            override fun areItemsTheSame(oldItem: LocationDTO, newItem: LocationDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: LocationDTO, newItem: LocationDTO): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return LocationViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}