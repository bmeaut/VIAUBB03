package hu.bme.aut.android.layoutdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.android.layoutdemo.databinding.EmptyLinearBinding
import hu.bme.aut.android.layoutdemo.databinding.LayoutItemBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:EmptyLinearBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmptyLinearBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            var bindingItem = LayoutItemBinding.inflate(layoutInflater)

            bindingItem.cbItem.text = binding.etTodo.text
            bindingItem.btnDelete.setOnClickListener {
                binding.root.removeView(bindingItem.root)
            }

            binding.root.addView(bindingItem.root)
        }
    }
}
