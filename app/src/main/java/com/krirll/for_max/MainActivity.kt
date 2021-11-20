package com.krirll.for_max

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = Adapter()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        val retrofit = Retrofit.Builder()
            .baseUrl("https://date.nager.at/") //добавляяем основной кусок нашей ссылки
            .addConverterFactory(GsonConverterFactory.create()) //дефолт, просто забей, это гугловский конвертер
            .build()
        val service = retrofit.create(ApiService::class.java)
        val text = SimpleDateFormat("yyyy", Locale.ROOT).format(Date())
        val call = service.getSchedule(text)
        call.enqueue(object : Callback<List<Holiday>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Holiday>>, response: Response<List<Holiday>>) {
                if (response.isSuccessful) {
                    adapter.setList(response.body()!!)
                    adapter.notifyDataSetChanged()
                }
                else
                    Toast.makeText(this@MainActivity,"something went wrong, check your Internet connection", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<List<Holiday>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"something went wrong, check your Internet connection", Toast.LENGTH_LONG).show()
            }

        })
    }
}