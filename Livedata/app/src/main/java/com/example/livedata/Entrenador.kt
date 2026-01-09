package com.example.livedata

import androidx.lifecycle.LiveData
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class Entrenador {

    interface EntrenadorListener {
        fun cuandoDeLaOrden(orden: String)
    }

    private val random = Random.Default
    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
    private var entrenando: ScheduledFuture<*>? = null

    val ordenLiveData = object : LiveData<String>() {
        override fun onActive() {
            super.onActive()
            iniciarEntrenamiento(object : EntrenadorListener {
                override fun cuandoDeLaOrden(orden: String) {
                    postValue(orden)
                }
            })
        }

        override fun onInactive() {
            super.onInactive()
            pararEntrenamiento()
        }
    }

    private fun iniciarEntrenamiento(entrenadorListener: EntrenadorListener) {
        if (entrenando == null || entrenando?.isCancelled == true) {
            var ejercicio = 0
            var repeticiones = -1

            entrenando = scheduler.scheduleWithFixedDelay({
                if (repeticiones < 0) {
                    repeticiones = random.nextInt(3) + 3
                    ejercicio = random.nextInt(5) + 1
                }

                val orden = "EJERCICIO$ejercicio:${if (repeticiones == 0) "CAMBIO" else repeticiones}"
                entrenadorListener.cuandoDeLaOrden(orden)
                repeticiones--
            }, 0, 1, TimeUnit.SECONDS)
        }
    }

    private fun pararEntrenamiento() {
        entrenando?.cancel(true)
    }
}