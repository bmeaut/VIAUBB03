package hu.bme.aut.android.listdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.listdemo.R
import hu.bme.aut.android.listdemo.model.Todo
import kotlinx.android.synthetic.main.layout_item.view.*

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.ViewHolder>(){

    val itemList : MutableList<Todo> = mutableListOf<Todo>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, null, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = itemList[position]
        holder.tvTitle.text = todo.title
        holder.cbItem.isChecked = todo.done
        holder.btnDelete.setOnClickListener {
            deleteTodo(position)
        }
    }

    private fun deleteTodo(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addTodo(todo:Todo) {
        itemList.add(todo)
        notifyItemInserted(itemList.lastIndex)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.tvTitle
        val cbItem = itemView.cbItem
        val btnDelete = itemView.btnDelete
    }
}