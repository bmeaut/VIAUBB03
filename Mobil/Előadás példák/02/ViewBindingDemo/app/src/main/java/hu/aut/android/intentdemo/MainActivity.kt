package hu.aut.android.intentdemo

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hu.aut.android.intentdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val button = findViewById<Button>(R.id.btnIntent)
//        button.setOnClickListener{}
        binding.btnIntent.setOnClickListener {
            startActivityForResult(Intent(this,SecondActivity::class.java),123)

            //intentSearch()

            //intentCall()

            //intentWaze()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            123 -> {
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this,data?.getIntExtra("answer",0).toString(),Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun intentSearch() {
        val intentSearch = Intent(Intent.ACTION_WEB_SEARCH)
        intentSearch.putExtra(SearchManager.QUERY, "Balaton")
        startActivity(intentSearch)
    }

    private fun intentCall() {
        val intentCall = Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:+361234567")
                )
        startActivity(intentCall)
    }

    private fun intentWaze() {
        //String wazeUri = "waze://?favorite=Home&navigate=yes";
        //val wazeUri = "waze://?ll=40.761043, -73.980545&navigate=yes"
        val wazeUri = "waze://?q=BME&navigate=yes"

        val intentTest = Intent(Intent.ACTION_VIEW)
        intentTest.data = Uri.parse(wazeUri)
        startActivity(intentTest)
    }
}
