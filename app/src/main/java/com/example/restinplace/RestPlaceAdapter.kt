package com.example.restinplace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restinplace.databinding.ItemLoadingBinding
import com.example.restinplace.databinding.ItemRestplaceBinding

class RestPlaceAdapter(val context: RestPlaceActivity, val restplaceDataList: ArrayList<RPlace>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {  //RestPlaceViewHolder
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
//    private val items = ArrayList<RPlace>()

    inner class LoadingViewHolder(private val binding: ItemLoadingBinding) :
            RecyclerView.ViewHolder(binding.root) {

            }

    // 뷰의 타입을 정해주는 곳이다.
    override fun getItemViewType(position: Int): Int {
        // 게시물과 프로그레스바 아이템뷰를 구분할 기준이 필요하다.
        return when (restplaceDataList[position].name) {
            " " -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {//RestPlaceViewHolder
//        val binding = ItemRestplaceBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        return RestPlaceViewHolder(binding)

        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRestplaceBinding.inflate(layoutInflater, parent, false)
                RestPlaceViewHolder(binding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        holder.bind(this.restplaceDataList[position])
        if(holder is RestPlaceViewHolder){
            holder.bind(restplaceDataList[position])
        }else{

        }
    }

    override fun getItemCount(): Int = restplaceDataList.size

    fun setList(notice: MutableList<RPlace>) {
        restplaceDataList.addAll(notice)
        restplaceDataList.add(RPlace(" ", " ")) // progress bar 넣을 자리
    }

    fun deleteLoading(){
        restplaceDataList.removeAt(restplaceDataList.lastIndex) // 로딩이 완료되면 프로그레스바를 지움
    }


}
