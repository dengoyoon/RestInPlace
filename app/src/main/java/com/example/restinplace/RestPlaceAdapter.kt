package com.example.restinplace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.restinplace.databinding.ItemRestplaceBinding

class RestPlaceAdapter (val context: Context, val restplaceDataList: ArrayList<RPlace>) : RecyclerView.Adapter<RestPlaceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestPlaceViewHolder {
        val binding = ItemRestplaceBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RestPlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestPlaceViewHolder, position: Int) {
        holder.bind(this.restplaceDataList[position])
    }

    override fun getItemCount(): Int = restplaceDataList.size

//    inner class RestPlaceViewHolder(val binding: ItemRestplaceBinding) : RecyclerView.ViewHolder(binding.root) {
//        //데이터와 뷰를 묶는다
//        fun bind(restplaceData : RPlace){
//            binding.restPlaceName.text = restplaceData.name
//            binding.restPlaceAddress.text = restplaceData.address
//        }
//    }
}
