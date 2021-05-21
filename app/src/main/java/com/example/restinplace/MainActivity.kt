package com.example.restinplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.restinplace.databinding.ActivityMainBinding
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.Thread.sleep
import java.util.Arrays.toString
import java.util.Objects.toString

private lateinit var binding: ActivityMainBinding

var handler = Handler(Looper.getMainLooper())

data class Route(val name1: String, val name2: String)

var routeDataList = arrayListOf<Route>()
var routeList = arrayListOf<String>()

class MainActivity : AppCompatActivity() {
    companion object {
        var baseUrl = "http://data.ex.co.kr/openapi/restinfo/"
        var key = "0781634483"
        var type = "json"
        var numOfRows = "99"

        //        var stdRestCd = "000555"
        var pageNo = null
        var routeCd = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(RipInterface::class.java)

        Thread {
            Thread.sleep(1000)
            for (i in 0..40) {
                val call = service.getRipData(key, type, i.toString(), numOfRows)
                call.enqueue(object : Callback<RipResponse> {
                    override fun onFailure(call: Call<RipResponse>, t: Throwable) {
                        Log.d("MainActivity", "result : " + t.message)
                        Log.d("MainActivity", "****************************************************FAIL")
                    }

                    override fun onResponse(call: Call<RipResponse>, response: Response<RipResponse>) {
                        if (response.code() == 200) {
                            val ripResponse = response.body()
                            Log.d("MainActivity", "result : " + RipResponse().toString())

                            for (j in 0..ripResponse!!.list.size - 2) {
                                if (ripResponse!!.list!!.get(j).routeNm.toString() != ripResponse!!.list!!.get(j + 1).routeNm.toString())
                                    if (ripResponse!!.list!!.get(j).routeNm.toString() !in routeList)
                                        routeList.add(ripResponse!!.list!!.get(j).routeNm.toString())
                            }
                        }

                    }
                })
            }//end API loading
        }.start()

        Thread {
            Thread.sleep(10000)
            Log.d("ROUTE LIST", routeList.toString())
            Log.d("ROUTE LIST", "sdfsdfdfsdf")
            handler.post {
                binding.highwaySelect.text = "떠나실 도로를 선택해 주세요"
            }
            Log.d("ROUTE LIST", routeList.toString())
            for (j in 0..routeList.size - 2 step 2) {
                routeDataList.add(Route(routeList.get(j), routeList.get(j + 1)))
            }

            handler.post {
                binding.routeList.adapter = RouteAdapter(this, routeDataList)
            }
        }.start()

    }

    override fun onStart() {
        super.onStart()
    }
}

interface RipInterface {
    @GET("restBestfoodList?")
    fun getRipData(
            @Query("key") key: String,
            @Query("type") type: String,
            @Query("pageNo") page_no: String,
            @Query("numOfRows") num_of_rows: String
//            @Query("stdRestCd")std_rest_cd:String
    ): Call<RipResponse> // DATA CLASS

}

class RipResponse() {
    @SerializedName("list")
    var list = ArrayList<RestPlace>()
}

class RestPlace {
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