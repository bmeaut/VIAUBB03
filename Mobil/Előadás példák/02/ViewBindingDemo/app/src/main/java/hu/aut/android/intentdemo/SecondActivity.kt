package hu.aut.android.intentdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.aut.android.intentdemo.databinding.ActivitySecondBinding
import hu.aut.android.intentdemo.databinding.ContentSecondBinding

class SecondActivity : AppCompatActivity() {
    lateinit var binding:ActivitySecondBinding
    lateinit var binding2 : ContentSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        binding2 = ContentSecondBinding.bind(binding.root)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding2.button1.setOnClickListener{

            var resultIntent: Intent = Intent()
            resultIntent.putExtra(
                    "answer", 1)
            setResult(RESULT_OK, resultIntent)
            finish()

        }

        binding2.button2.setOnClickListener{

            var resultIntent: Intent = Intent()
            resultIntent.putExtra(
                    "answer", 2)
            setResult(RESULT_OK, resultIntent)
            finish()

        }

    }

}
