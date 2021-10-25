//
//  TodoRow.swift
//  iOSTodoApp
//
//  Created by Ro, Jean on 10/23/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import sharedTodo

struct TodoRow: View {
    var todoItem: Todo

    var body: some View {
        HStack {
            Toggle(todoItem.title, isOn: Binding.constant(todoItem.completed)).onTapGesture {
                // implement details view
            }
        }.padding()
    }
}

struct TodoRow_Previews: PreviewProvider {
    static var previews: some View {
        TodoRow(todoItem: Todo(id: 1, title: "Test", completed: true))
    }
}
