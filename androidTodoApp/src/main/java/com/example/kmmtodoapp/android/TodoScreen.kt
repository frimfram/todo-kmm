package com.example.kmmtodoapp.android

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kmmtodoapp.Todo
import kotlin.random.Random
import androidx.compose.material.Switch

/**
 * Stateless component that is responsible for the entire todo screen.
 *
 * @param items (state) list of [TodoItem] to display
 * @param currentlyEditing (state) enable edit mode for this item
 * @param onAddItem (event) request an item be added
 * @param onRemoveItem (event) request an item be removed
 * @param onStartEdit (event) request an item start edit mode
 * @param onEditItemChange (event) request the current edit item be updated
 * @param onEditDone (event) request edit mode completion
 */
@Composable
fun TodoScreen(
    items: List<Todo>,
    currentlyEditing: Todo?,
    onAddItem: (Todo) -> Unit,
    onRemoveItem: (Todo) -> Unit,
    onStartEdit: (Todo) -> Unit,
    onEditItemChange: (Todo) -> Unit,
    onEditDone: () -> Unit
) {
    Column {
        val enableTopSection = currentlyEditing == null
        TodoItemInputBackground(elevate = enableTopSection) {
            if (enableTopSection) {
                TodoItemEntryInput(onAddItem)
            } else {
                Text(
                    text = "Editing item",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items = items) { todo ->
                if (currentlyEditing?.id == todo.id) {
                    TodoItemInlineEditor(
                        item = currentlyEditing,
                        onEditItemChange = onEditItemChange,
                        onEditDone = onEditDone,
                        onRemoveItem = { onRemoveItem(todo) }
                    )
                } else {
                    TodoRow(
                        todo = todo,
                        onItemClicked = { onStartEdit(it) },
                        onCompletionToggle = { },
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }
        }
    }
}

/**
 * Stateless composable that provides a styled [TodoItemInput] for inline editing.
 *
 * This composable will display a floppy disk and ❌ for the buttons.
 *
 * @param item (state) the current item to display in editor
 * @param onEditItemChange (event) request item be changed
 * @param onEditDone (event) request edit mode completion for this item
 * @param onRemoveItem (event) request this item be removed
 */
@Composable
fun TodoItemInlineEditor(
    item: Todo,
    onEditItemChange: (Todo) -> Unit,
    onEditDone: () -> Unit,
    onRemoveItem: () -> Unit
) {
    TodoItemInput(
        text = item.title,
        onTextChange = { onEditItemChange(item.copy(title = it)) },
        completed = item.completed,
        onCompletionToggle = { onEditItemChange(item.copy(completed = it)) },
        submit = onEditDone,
        buttonSlot = {
            Row {
                val shrinkButtons = Modifier.widthIn(20.dp)
                TextButton(onClick = onEditDone, modifier = shrinkButtons) {
                    Text(
                        "\uD83D\uDCBE", // floppy disk
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(30.dp)
                    )
                }
                TextButton(onClick = onRemoveItem, modifier = shrinkButtons) {
                    Text(
                        "❌",
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(30.dp)
                    )
                }
            }
        }
    )
}

/**
 * Stateful composable to allow entry of *new* [TodoItem].
 *
 * This composable will display a button with [buttonText].
 *
 * @param onItemComplete (event) notify the caller that the user has completed entry of an item
 * @param buttonText text to display on the button
 */
@Composable
fun TodoItemEntryInput(onItemComplete: (Todo) -> Unit, buttonText: String = "Add") {
    val (text, onTextChange) = rememberSaveable { mutableStateOf("") }
    val (completed, onCompletionToggle) = remember { mutableStateOf(false) }

    val submit = {
        if (text.isNotBlank()) {
            onItemComplete(Todo(10, text, completed))
            onTextChange("")
            onCompletionToggle(completed)
        }
    }

    TodoItemInput(
        text = text,
        onTextChange = onTextChange,
        completed,
        onCompletionToggle,
        submit = submit
    ) {
        TodoEditButton(onClick = submit, text = buttonText, enabled = text.isNotBlank())
    }
}

/**
 * Stateless input composable for editing [Todo].
 *
 * @param text (state) current text of the item
 * @param onTextChange (event) request the text change
 * @param icon (state) current selected icon for the item
 * @param onIconChange (event) request the current icon change
 * @param submit (event) notify the caller that the user has submitted with [ImeAction.Done]
 * @param iconsVisible (state) display icons or hide them
 * @param buttonSlot (slot) slot for providing buttons next to the text
 */
@Composable
fun TodoItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    completed: Boolean,
    onCompletionToggle: (Boolean) -> Unit,
    submit: () -> Unit,
    buttonSlot: @Composable () -> Unit,
) {
    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .height(IntrinsicSize.Min)
        ) {
            TodoInputText(
                text = text,
                onTextChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onImeAction = submit
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(Modifier.align(Alignment.CenterVertically)) { buttonSlot() }
        }
        Spacer(Modifier.height(16.dp))
    }
}

/**
 * Stateless composable that displays a full-width [Todo].
 *
 * @param todo item to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param modifier modifier for this element
 * @param iconAlpha alpha tint to apply to icon, by default random between 0.3 and 0.9
 */
@Composable
fun TodoRow(
    todo: Todo,
    onItemClicked: (Todo) -> Unit,
    onCompletionToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .clickable { onItemClicked(todo) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(todo.title)
        Switch(checked = todo.completed, onCheckedChange = onCompletionToggle)
    }
}


@Preview
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        Todo(1,"Learn compose", false),
        Todo(2, "Attend the workshop", false),
        Todo(3,"Grocery shopping", true),
        Todo(4,"Build dynamic UIs", true)
    )
    TodoScreen(items, null, {}, {}, {}, {}, {})
}

@Preview
@Composable
fun PreviewTodoItemInput() = TodoItemEntryInput(onItemComplete = { })

@Preview
@Composable
fun PreviewTodoRow() {
    val todo = Todo(5,"Visit Seattle", true)
    TodoRow(todo = todo, onItemClicked = {}, onCompletionToggle = {}, modifier = Modifier.fillMaxWidth())
}
