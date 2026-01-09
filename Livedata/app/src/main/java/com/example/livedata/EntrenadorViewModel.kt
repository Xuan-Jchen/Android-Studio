package com.example.livedata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap


class EntrenadorViewModel(application: Application) : AndroidViewModel(application) {

    private val entrenador = Entrenador()
    private var ejercicioAnterior = ""

    val ejercicioLiveData: LiveData<Int> = entrenador.ordenLiveData.switchMap { orden: String ->
        val ejercicio = orden.split(":")[0]
        if (ejercicio != ejercicioAnterior) {
            ejercicioAnterior = ejercicio

            val imagen = when (ejercicio) {
                "EJERCICIO1" -> R.drawable.e1
                "EJERCICIO2" -> R.drawable.e2
                "EJERCICIO3" -> R.drawable.e3
                "EJERCICIO4" -> R.drawable.e4
                else -> R.drawable.e1
            }

            MutableLiveData(imagen)
        } else {
            null
        }
    }

    val repeticionLiveData: LiveData<String> = entrenador.ordenLiveData.map { orden: String ->
        orden.split(":")[1]
    }
}