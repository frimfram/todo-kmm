package com.example.kmmtodoapp

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}