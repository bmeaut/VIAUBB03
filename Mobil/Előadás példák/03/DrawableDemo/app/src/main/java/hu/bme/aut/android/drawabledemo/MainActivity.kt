package hu.bme.aut.android.drawabledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import hu.bme.aut.android.drawabledemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val showAnim = AnimationUtils.loadAnimation(this,R.anim.tweenanim)
        binding.btn1.startAnimation(showAnim)
    }

}
