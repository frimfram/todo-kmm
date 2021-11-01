package com.example.kmmtodoapp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kmmtodoapp.Greeting
import android.widget.TextView
import androidx.activity.viewModels
import androidx.activity.compose.setContent
import com.example.kmmtodoapp.android.ui.TodoKMMAppTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import org.koin.androidx.viewmodel.ext.android.getViewModel

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    
    val todoViewModel by viewModels<TodoViewModel>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoKMMAppTheme {
                Surface {
                    TodoActivityScreen(todoViewModel = todoViewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        todoViewModel.loadData()
    }
}

@Composable
private fun TodoActivityScreen(todoViewModel: TodoViewModel) {
    TodoScreen(
        items = todoViewModel.todoItems,
        currentlyEditing = todoViewModel.currentEditItem,
        onAddItem = todoViewModel::addItem,
        onRemoveItem = todoViewModel::removeItem,
        onStartEdit = todoViewModel::onEditItemSelected,
        onEditItemChange = todoViewModel::onEditItemChange,
        onEditDone = todoViewModel::onEditDone
    )
}
