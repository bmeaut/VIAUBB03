package hu.aut.android.asynctaskhttpdemo

import android.os.Bundle
import org.json.JSONException
import org.json.JSONObject
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.widget.Toast
import hu.aut.android.asynctaskhttpdemo.retrofit.CurrencyExchangeAPI
import hu.aut.android.asynctaskhttpdemo.retrofit.MoneyResult
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import hu.aut.android.asynctaskhttpdemo.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    private val URL_BASE = "https://api.exchangeratesapi.io/latest?base=EUR"

    private val brWeatherReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val rawResult = intent.getStringExtra(
                HttpGetTask.KEY_RESULT
            )

            try {
                val rawJson = JSONObject(rawResult)
                val hufValue = rawJson.getJSONObject("rates").getString("HUF")
                binding.tvResult.text = hufValue

            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.exchangeratesapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val currencyAPI = retrofit.create(CurrencyExchangeAPI::class.java)

        binding.btnGetRate.setOnClickListener {

            //httpGetAsyncTask()
            //httpGetAsyncTaskCallback()
            //httpGetOkHttp()
            httpGetRetrofit(currencyAPI)
        }
    }

    private fun httpGetAsyncTask() {
        HttpGetTask(applicationContext).execute(URL_BASE)
    }

    private fun httpGetAsyncTaskCallback() {
        HttpGetTaskWithCalback { result ->
            try {
                val rawJson = JSONObject(result)
                val hufValue = rawJson.getJSONObject("rates").getString("HUF")
                binding.tvResult.text = hufValue

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }.execute(URL_BASE)
    }

    private fun httpGetOkHttp() {
        Thread {
            var data = OkHttpHelper.getRates()
            runOnUiThread {
                Toast.makeText(this@MainActivity, data, Toast.LENGTH_LONG).show()
            }
        }.start()
    }

    private fun httpGetRetrofit(currencyAPI: CurrencyExchangeAPI) {
        val ratesCall = currencyAPI.getRates("EUR")
        ratesCall.enqueue(object : Callback<MoneyResult> {
            override fun onFailure(call: Call<MoneyResult>, t: Throwable) {
                binding.tvResult.text = t.message
            }

            override fun onResponse(call: Call<MoneyResult>, response: Response<MoneyResult>) {
                binding.tvResult.text = response.body()?.rates?.HUF.toString()
            }
        })
    }
    

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            brWeatherReceiver,
            IntentFilter(HttpGetTask.FILTER_RESULT)
        )
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(
            this
        ).unregisterReceiver(brWeatherReceiver)
    }
}

