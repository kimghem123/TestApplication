package com.example.testapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)

        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit1: Retrofit = Retrofit.Builder()
            .baseUrl("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/")
            .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
            .client(client)
            .build()

        val service = retrofit1.create(RetrofitService::class.java)
        test.setOnClickListener {
            service.getByRealm(
                "GQCTPd0CU10YiZJcv5MqRsuaI%2BmywZXRR0M0RZ8rolVq10i3ugfDwSQV4%2FCaYH9dX2pZKccjPJH9j%2FGTiaZcfw%3D%3D",
                "1",
                "1",
                "B000",
                "1",
                "10",
                "20170101",
                "20231231"
            )
                .enqueue(object : Callback<Festival>{
                    override fun onResponse(call: Call<Festival>, response: Response<Festival>) {
                        if(response.isSuccessful){
                            Log.d("test1","success")
                        }
                    }

                    override fun onFailure(call: Call<Festival>, t: Throwable) {
                        Log.d("test1",t.toString())
                    }
                })
        }

    }
}