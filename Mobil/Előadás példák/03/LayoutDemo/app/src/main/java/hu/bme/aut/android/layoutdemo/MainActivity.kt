package hu.bme.aut.android.layoutdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.empty_linear.*
import kotlinx.android.synthetic.main.layout_item.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.empty_linear)

        btnAdd.setOnClickListener{

            if (etTodo.text.isEmpty())
                etTodo.error = "Adjon meg nevet!"
            else {

                val item = layoutInflater.inflate(R.layout.layout_item, null, false)

                item.cbItem.text = etTodo.text
                item.btnDelete.setOnClickListener {
                    layoutContent.removeView(item)
                }

                layoutContent.addView(item)
            }
        }

    }
}
