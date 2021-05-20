package com.example.restinplace

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.FragmentActivity
import com.example.restinplace.databinding.ItemRouteBinding

class RouteAdapter(val context: FragmentActivity?, val routeDataList: ArrayList<Route>) :BaseAdapter() {
    val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    lateinit var binding: ItemRouteBinding

    override fun getCount(): Int = routeDataList.size

    override fun getItem(position: Int) = routeDataList[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        binding = ItemRouteBinding.inflate(inflater, parent, false)
        binding.leftButton.text = routeDataList[position].name1
        binding.rightButton.text = routeDataList[position].name2

        binding.leftButton.setOnClickListener {
            val intent = Intent(context, RestPlaceActivity::class.java)
            intent.putExtra("routeNm", routeDataList[position].name1)
            context?.startActivity(intent)
        }
        binding.rightButton.setOnClickListener {
            val intent = Intent(context, RestPlaceActivity::class.java)
            intent.putExtra("routeNm", routeDataList[position].name2)
            context?.startActivity(intent)
        }

        return binding.root
    }

}