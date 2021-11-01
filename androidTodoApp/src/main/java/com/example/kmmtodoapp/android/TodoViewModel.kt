package com.example.kmmtodoapp.android

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmmtodoapp.TodoRepository
import com.example.kmmtodoapp.Todo
import kotlinx.coroutines.launch

class TodoViewModel(private val todosRepository: TodoRepository = TodoRepository()) : ViewModel() {

    private var currentEditPosition by mutableStateOf(-1)

    var todoItems = mutableStateListOf<Todo>()
        private set
    
    fun loadData() {
        viewModelScope.launch {
            todosRepository.getAllTodos(true).also {
                todoItems.clear()
                todoItems.addAll(it)
            }
        }
    }

    val currentEditItem: Todo?
        get() = todoItems.getOrNull(currentEditPosition)

    fun addItem(item: Todo) {
        todoItems.add(item)
    }

    fun removeItem(item: Todo) {
        todoItems.remove(item)
        onEditDone() // don't keep the editor open when removing items
    }

    fun onEditItemSelected(item: Todo) {
        currentEditPosition = todoItems.indexOf(item)
    }

    fun onEditDone() {
        currentEditPosition = -1
    }

    fun onEditItemChange(item: Todo) {
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id) {
            "Error"
        }

        todoItems[currentEditPosition] = item
    }
}