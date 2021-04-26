package hu.bme.aut.android.listdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.listdemo.databinding.LayoutItemBinding
import hu.bme.aut.android.listdemo.model.Todo

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    val itemList: MutableList<Todo> = mutableListOf<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = itemList[position]

        holder.binding.tvTitle.text = todo.title
        holder.binding.cbItem.isChecked = todo.done
        holder.binding.btnDelete.setOnClickListener {
            deleteTodo(position)
        }
    }

    private fun deleteTodo(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addTodo(todo: Todo) {
        itemList.add(todo)
        notifyItemInserted(itemList.lastIndex)
    }


    class ViewHolder(val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {    }
}