package com.example.vmmv

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executors

class MiHipotecaViewModel(application: Application) : AndroidViewModel(application) {

    private val executor = Executors.newSingleThreadExecutor()
    private val simulador = SimuladorHipoteca()

    val cuota = MutableLiveData<Double>()
    val errorCapital = MutableLiveData<Double?>()
    val errorPlazos = MutableLiveData<Int?>()
    val calculando = MutableLiveData<Boolean>()

    init {
        calculando.value = false
    }

    fun calcular(capital: Double, plazo: Int) {
        val solicitud = SimuladorHipoteca.Solicitud(capital, plazo)

        executor.execute {
            simulador.calcular(solicitud, object : SimuladorHipoteca.Callback {

                override fun cuandoEmpieceElCalculo() {
                    calculando.postValue(true)
                    // 清空之前的错误
                    errorCapital.postValue(null)
                    errorPlazos.postValue(null)
                }

                override fun cuandoEsteCalculadaLaCuota(cuotaResultante: Double) {
                    cuota.postValue(cuotaResultante)
                }

                override fun cuandoHayaErrorDeCapitalInferiorAlMinimo(capitalMinimo: Double) {
                    errorCapital.postValue(capitalMinimo)
                }

                override fun cuandoHayaErrorDePlazoInferiorAlMinimo(plazoMinimo: Int) {
                    errorPlazos.postValue(plazoMinimo)
                }

                override fun cuandoFinaliceElCalculo() {
                    calculando.postValue(false)
                }
            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        executor.shutdown()
    }
}