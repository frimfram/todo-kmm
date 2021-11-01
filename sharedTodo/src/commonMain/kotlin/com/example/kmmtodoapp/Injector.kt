package com.example.kmmtodoapp

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    modules(commonModule)
    appDeclaration()
}

fun initKoin() = initKoin {  }

val commonModule = module { 
    single { TodoApi() }
    single { TodoRepository() }
}