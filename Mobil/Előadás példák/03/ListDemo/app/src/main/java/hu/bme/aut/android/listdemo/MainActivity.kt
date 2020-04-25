package hu.bme.aut.android.listdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.listdemo.adapter.TodoAdapter
import hu.bme.aut.android.listdemo.model.Todo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_item.view.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter : TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener{

            if (etTodo.text.isEmpty())
                etTodo.error = "Adjon meg nevet!"
            else {

                adapter.addTodo(Todo(etTodo.text.toString(),false))
            }
        }

        adapter = TodoAdapter()
        rvTodo.layoutManager = GridLayoutManager(this,3)
        rvTodo.adapter = adapter

    }
}
