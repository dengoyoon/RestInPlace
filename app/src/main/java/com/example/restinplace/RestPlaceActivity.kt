package com.example.restinplace

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.restinplace.databinding.ActivityRestPlaceBinding
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private lateinit var binding: ActivityRestPlaceBinding


data class RPlace(val name: String, val address: String)
var restplaceDataList = arrayListOf<RPlace>()
var restplaceList = arrayListOf<RPlace>()

class RestPlaceActivity : AppCompatActivity() {
    companion object {
        var baseUrl = "http://data.ex.co.kr/openapi/restinfo/"
        var key = "0781634483"
        var type = "json"
        var numOfRows = "30"
    }

    private lateinit var adapter: RestPlaceAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("&&&&&&&&&&&&&&&&ARRAY SIZE", restplaceDataList.size.toString())

        val retrofit = Retrofit.Builder()
            .baseUrl(RestPlaceActivity.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RipInterface2::class.java)

        var routeName = "" + intent.getStringExtra("routeNm")

        var pageNo = 1


        val call = service.getRipData2(
            RestPlaceActivity.key,
            RestPlaceActivity.type,
            routeName,
            RestPlaceActivity.numOfRows,
            pageNo.toString()
        )
        call.enqueue(object : Callback<RipResponse2> {
            override fun onFailure(call: Call<RipResponse2>, t: Throwable) {
                Log.d("MainActivity", "result : " + t.message)
            }

            override fun onResponse(call: Call<RipResponse2>, response: Response<RipResponse2>) {
                if (response.code() == 200) {
                    val ripResponse = response.body()
                    for (i in 0..29) {
                        Log.d("RESPONSE", ripResponse!!.list.get(i).stdRestNm.toString())
                        Log.d("RESPONSE", ripResponse!!.list.get(i).svarAddr.toString())

                        restplaceDataList.add(
                            RPlace(
                                ripResponse!!.list.get(i).stdRestNm.toString(),
                                ripResponse!!.list.get(i).svarAddr.toString()
                            )
                        )

                    }
                }

            }

        })

        Thread {
            Thread.sleep(2000)

            adapter = RestPlaceAdapter(this, restplaceDataList)
            handler.post {
                binding.restPlaceList.adapter = adapter
                binding.restPlaceSelect.text = routeName
            }
        }.start()


        binding.restPlaceList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // 스크롤이 끝에 도달했는지 확인
                if (!binding.restPlaceList.canScrollVertically(1)) {
                    Log.d("PAGE NO",pageNo.toString())
                    pageNo += 1
                }


            }
        })




//        for(i in 1..10){
//            restplaceDataList.add(RPlace("행복휴게소","행복시 행복동 행복구 42번지"))
//        }

//        adapter = RestPlaceAdapter(this, restplaceDataList)
//        binding.restPlaceList.adapter = adapter
    }

}

interface RipInterface2 {
    @GET("restBestfoodList?")
    fun getRipData2(
            @Query("key") key: String,
            @Query("type") type: String,
            @Query("routeNm") routeNm: String?,
            @Query("numOfRows") numOfRows: String?,
            @Query("pageNo") pageNo: String?,
//            @Query("stdRestCd")std_rest_cd:String
    ): Call<RipResponse2> // DATA CLASS

}

class RipResponse2() {
    @SerializedName("list")
    var list = ArrayList<RestPlace>()
    @SerializedName("pageSize")
    var pageSize:String? = null
}

class RestPlace2 {
    @SerializedName("stdRestCd")
    var stdRestCd: String? = null

    @SerializedName("routeCd")
    var routeCd: String? = null

    @SerializedName("svarAddr")
    var svarAddr: String? = null

    @SerializedName("routeNm")
    var routeNm: String? = null

    @SerializedName("stdRestNm")
    var stdRestNm: String? = null

    @SerializedName("foodNm")
    var foodNm: String? = null

    @SerializedName("foodCost")
    var foodCost: String? = null

    @SerializedName("etc")
    var etc: String? = null

    @SerializedName("recommendyn")
    var recommendyn: String? = null

    @SerializedName("seasonMenu")
    var seasonMenu: String? = null

    @SerializedName("bestfoodyn")
    var bestfoodyn: String? = null

    @SerializedName("premiumyn")
    var premiumyn: String? = null

    @SerializedName("foodMaterial")
    var foodMeterial: String? = null

}
