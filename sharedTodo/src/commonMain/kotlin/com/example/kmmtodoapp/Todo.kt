package com.example.kmmtodoapp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Todo(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("completed")
    val completed: Boolean
)