package com.justai.jaicf.template

import org.json.JSONArray
import org.json.JSONObject


public class Weather {

    var dataModel = DataModel()
    var obj : JSONObject = JSONObject()

    fun getWeatherData(message: String): String {

        try {
            val respo = khttp.get("https://api.openweathermap.org/data/2.5/weather?q=$message&units=metric&appid=f3b837317235d4ebe105b7a05a5656eb")
            obj = respo.jsonObject
        } catch (e: Exception) {
            return "Ошибка запроса, попробуй снова"
        }

        try {
            val jarray: JSONArray = obj.getJSONArray("weather")
            var i = 0
            while (i < jarray.length()){
                val jobj: JSONObject = jarray.getJSONObject(i)
                dataModel.main = jobj.get("main").toString()
                i++
            }
        } catch (e: Exception) {
            return "Город не найден"
        }

        try {
            val info: JSONObject =obj.getJSONObject("main")
            dataModel.temperature = info.getDouble("temp")
            dataModel.humidity = info.getDouble("humidity")
        } catch (e: Exception) {
            return "Ошибка"
        }

        return "Mainly ${dataModel.main}. Temperature is ${dataModel.temperature} C. Humidity is ${dataModel.humidity} %"

    }


     fun getWeatherIcon(message: String ): String {
         try {
             val respo = khttp.get("https://api.openweathermap.org/data/2.5/weather?q=$message&units=metric&appid=f3b837317235d4ebe105b7a05a5656eb")
             obj = respo.jsonObject
         } catch (e: Exception) {
             return "Ошибка запроса, попробуй снова"
         }
         try {
             val jarray: JSONArray = obj.getJSONArray("weather")
             var i = 0
             while (i < jarray.length()){
                 val jobj: JSONObject = jarray.getJSONObject(i)
                 dataModel.icon =jobj.get("icon").toString()
                 i++
             }
         } catch (e: Exception) {
             return "Город не найден"
         }

         return "http://openweathermap.org/img/wn/${dataModel.icon}.png"
    }



}
