package com.example.kmmtodoapp.android.di

import com.example.kmmtodoapp.TodoRepository
import com.example.kmmtodoapp.android.TodoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { TodoViewModel(get()) }
}