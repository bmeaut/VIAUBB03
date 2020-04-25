package hu.bme.aut.android.drawabledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showAnim = AnimationUtils.loadAnimation(this,R.anim.tweenanim)
        btn1.startAnimation(showAnim)
    }

}
