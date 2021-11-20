package com.krirll.for_max

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class Holiday {

    @SerializedName("date") //такие штуки нужны, чтобы retrofit сам искал нужное поле
    @Expose //дефолтная строка для конвертации с помощью Gson, не обращай внимание
    val date : String? = null

    @SerializedName("localName")
    @Expose
    val name : String? = null

}



interface ApiService {
    //теперь мы сформировали модель и знаем что будем получать
    @GET("api/v3/publicholidays/{year}/RU")
    fun getSchedule(@Path("year") currentYear : String) : Call<List<Holiday>>
}
