package com.example.restinplace

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.restinplace.databinding.ItemRestplaceBinding

class RestPlaceViewHolder(val binding: ItemRestplaceBinding) : RecyclerView.ViewHolder(binding.root) {
    //데이터와 뷰를 묶는다
    fun bind(restplaceData : RPlace){
        binding.restPlaceName.text = restplaceData.name
        binding.restPlaceAddress.text = restplaceData.address
    }
}