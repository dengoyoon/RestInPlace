package com.example.restinplace

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

class RestPlaceActivity : AppCompatActivity() {
    companion object {
        var baseUrl = "http://data.ex.co.kr/openapi/restinfo/"
        var key = "0781634483"
        var type = "json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("&&&&&&&&&&&&&&&&ARRAY SIZE" , restplaceDataList.size.toString())

        val retrofit = Retrofit.Builder()
                .baseUrl(RestPlaceActivity.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(RipInterface2::class.java)
//
        var routeNm = "" + intent.getStringExtra("routeNm")
//
        binding.restPlaceSelect.text = routeNm



        val call = service.getRipData2(RestPlaceActivity.key, RestPlaceActivity.type, routeNm)
        call.enqueue(object : Callback<RipResponse2> {
            override fun onFailure(call: Call<RipResponse2>, t: Throwable) {
                Log.d("MainActivity", "******************************result : " + t.message)
            }

            override fun onResponse(call: Call<RipResponse2>, response: Response<RipResponse2>) {
                if (response.code() == 200) {
                    val ripResponse = response.body()


                }

            }

        })

        for(i in 1..10){
            restplaceDataList.add(RPlace("행복휴게소","행복시 행복동 행복구 42번지"))
        }

        val adap = RestPlaceAdapter(this, restplaceDataList)
        binding.restPlaceList.adapter = adap
    }

}

interface RipInterface2 {
    @GET("restBestfoodList?")
    fun getRipData2(
            @Query("key") key: String,
            @Query("type") type: String,
            @Query("routeNm") routeNm: String?,
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
