//
//  TodoViewModel.swift
//  iOSTodoApp
//
//  Created by Ro, Jean on 10/23/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import sharedTodo

class TodoViewModel: ObservableObject {
    @Published var todos = TodoViewState.loading

    private let repo: TodoRepository

    init(repository: TodoRepository) {
        repo = repository
    }

    func loadTodos(force: Bool) {
        todos = TodoViewState.loading
        repo.getAllTodos(force: force, completionHandler: { todos, error in
            if let todos = todos {
                self.todos = TodoViewState.result(todos)
            } else {
                self.todos = TodoViewState.error(error?.localizedDescription ?? "error")
            }
        })
    }
}

enum TodoViewState {
    case loading
    case result([Todo])
    case error(String)
}
