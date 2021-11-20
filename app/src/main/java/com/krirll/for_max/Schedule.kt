package com.krirll.for_max

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class Schedule {

    @SerializedName("name") //такие штуки нужны, чтобы retrofit сам искал нужное поле
    @Expose //дефолтная строка для конвертации с помощью Gson, не обращай внимание
    val seriaNumber : String? = null

    @SerializedName("airdate")
    @Expose
    val date : String? = null

    @SerializedName("airtime")
    @Expose
    val time : String? = null

    @SerializedName("show") //а вот тут проблема, когда подключимся объясню (это вложенный класс)
    @Expose
    val showDescription : ShowDescription? = null

}

class ShowDescription {

    @SerializedName("name")
    @Expose
    val nameOfSerial : String? = null

    @SerializedName("network")
    @Expose
    val network : TV? = null

}

class TV {

    @SerializedName("name")
    @Expose
    val nameOfTV : String? = null

}


interface ApiService {
    //теперь мы сформировали модель и знаем что будем получать
    @GET("schedule?country=RU")
    fun getSchedule(@Query("date") currentDate : String) : Call<List<Schedule>>
}
