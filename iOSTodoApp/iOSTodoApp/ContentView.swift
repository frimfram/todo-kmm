import SwiftUI
import sharedTodo

struct ContentView: View {
    @ObservedObject private var viewModel = TodoViewModel(repository: TodoRepository())
    @State private var todoText = ""

	let greet = Greeting().greeting()

	var body: some View {
        NavigationView {
            VStack {
                listView
                    .navigationBarTitle("Todos   \(greet)")
                    .navigationBarItems(trailing: Button("Reload") {
                        viewModel.loadTodos(force: true)
                    })
                HStack {
                    TextField("enter new todo", text: $todoText)
                    Button("Submit") {
                        // todo:  save the new item
                        todoText = ""
                    }
                }.padding()
            }
        }.onAppear {
            viewModel.loadTodos(force: false)
        }
	}

    @ViewBuilder var listView: some View {
        switch viewModel.todos {
        case TodoViewState.loading: Text("Loading...").multilineTextAlignment(.center)
        case TodoViewState.result(let todos):
            List(todos) { todoItem in
                TodoRow(todoItem: todoItem)
            }
        case TodoViewState.error(let desc):
            Text(desc).multilineTextAlignment(.center)
        }
    }
}

extension Todo: Identifiable {}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
