package com.krirll.for_max

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var db : DataBase? = null
    private var list : List<Holiday> = listOf()
    private val adapter = Adapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db =
            Room.databaseBuilder(this, DataBase::class.java, "database")
                .build()
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
                    list = response.body()!!
                    adapter.setList(list)
                    adapter.notifyDataSetChanged()
                    saveData()
                } else {
                    findViewById<TextView>(R.id.textView).text = getString(R.string.error)
                    getSavedData()
                }
            }
            override fun onFailure(call: Call<List<Holiday>>, t: Throwable) {
                findViewById<TextView>(R.id.textView).text = getString(R.string.error)
                getSavedData()
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getSavedData() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val savedList = db?.localDao()?.getAll()
                val newList = mutableListOf<Holiday>()
                for (item in savedList!!) newList.add(Holiday(item.date, item.name))
                adapter.setList(newList)
                list = newList
            }
            adapter.setList(list)
            adapter.notifyDataSetChanged()
        }
    }

    private fun saveData() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                db?.localDao()?.deleteAll()
                if (list.isNotEmpty())
                    list.forEach {
                        db?.localDao()?.insert(
                            LocalModel(
                                it.date!!,
                                it.name!!
                            )
                        )
                    }
            }
        }
    }

    override fun onPause() {
        saveData()
        super.onPause()
    }
}