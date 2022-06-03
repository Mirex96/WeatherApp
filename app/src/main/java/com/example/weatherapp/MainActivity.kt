package com.example.weatherapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

const val API_KEY = "48a78ead2e1d46ecb9f94506222505"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonGet = findViewById<Button>(R.id.buttonGet)
        buttonGet.setOnClickListener {
            getResult()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getResult() {
        val countries = findViewById<EditText>(R.id.country)
        val name = countries.text.toString()
        val time = findViewById<TextView>(R.id.time)
        val url = "https://api.weatherapi.com/v1/current.json?" +
                "key=$API_KEY&q=$name&aqi=no"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            { response ->
                val obj = JSONObject(response)
                val temp = obj.getJSONObject("current")
                val tempC = temp.getString("temp_c")
                val timeC = temp.getString("last_updated")
                val result = findViewById<TextView>(R.id.result)
                result.text = "Погода в $name: $tempC"
                Log.d("VB", "response: ${temp.getString("temp_c")}")

                time.text = "Последнее обновление в: $timeC"
                Log.d("VB", "response: ${temp.getString("last_updated")}")


            },
            {
                Log.d("VB", "ERROR: $it")
            }
        )
        queue.add(stringRequest)

    }
}