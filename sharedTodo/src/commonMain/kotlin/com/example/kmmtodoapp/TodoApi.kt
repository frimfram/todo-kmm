package com.example.kmmtodoapp

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

class TodoApi {
    
    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getAllTodos(): List<Todo> {
        return httpClient.get(ENDPOINT)
    }
    
    companion object {
        private const val ENDPOINT="https://jsonplaceholder.typicode.com/users/1/todos"
    }
}