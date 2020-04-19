package hu.bme.aut.android.helloandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myTextVeiw.setOnClickListener {
            myTextVeiw.append("\nCLICKED!")

            Toast.makeText(this,System.currentTimeMillis().toString(),Toast.LENGTH_LONG).show()
        }
    }
}
