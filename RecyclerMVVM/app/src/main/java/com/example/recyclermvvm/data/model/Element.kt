package com.example.recyclermvvm.data.model

data class Element(

    val id: Int,
    val nom: String,
    val descripcio: String,
    var valoracio: Float = 0f,
    val categoria: String? = null
)
