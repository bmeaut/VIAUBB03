package hu.aut.android.intentdemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.content_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setSupportActionBar(toolbar)

        button1.setOnClickListener{

            var resultIntent: Intent = Intent()
            resultIntent.putExtra(
                    "answer", 1)
            setResult(RESULT_OK, resultIntent)
            finish()

        }

        button2.setOnClickListener{

            var resultIntent: Intent = Intent()
            resultIntent.putExtra(
                    "answer", 2)
            setResult(RESULT_OK, resultIntent)
            finish()

        }

    }

}
