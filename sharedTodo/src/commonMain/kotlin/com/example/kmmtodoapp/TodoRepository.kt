package com.example.kmmtodoapp

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TodoRepository : KoinComponent {
    private val api: TodoApi by inject()
    private val todos: MutableList<Todo> = mutableListOf()
    
    @Throws(Exception::class)
    suspend fun getAllTodos(force: Boolean): List<Todo> {
        return if (todos.isNotEmpty() && !force) {
            todos
        } else {
            api.getAllTodos().also {
                todos.clear()
                todos.addAll(it)
            }
        }
    }
}