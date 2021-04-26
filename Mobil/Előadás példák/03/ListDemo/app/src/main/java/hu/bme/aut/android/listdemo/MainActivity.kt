package hu.bme.aut.android.listdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import hu.bme.aut.android.listdemo.adapter.TodoAdapter
import hu.bme.aut.android.listdemo.databinding.ActivityMainBinding
import hu.bme.aut.android.listdemo.model.Todo

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var adapter : TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener{

            if (binding.etTodo.text.isEmpty())
                binding.etTodo.error = "Adjon meg nevet!"
            else {

                adapter.addTodo(Todo(binding.etTodo.text.toString(),false))
            }
        }

        adapter = TodoAdapter()
        binding.rvTodo.layoutManager = GridLayoutManager(this,3)
        binding.rvTodo.adapter = adapter

    }
}
