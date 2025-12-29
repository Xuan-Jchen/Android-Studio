package com.example.vmmv

import kotlin.math.pow

class SimuladorHipoteca {

    class Solicitud(
        val capital: Double,
        val plazo: Int
    )

    interface Callback {
        fun cuandoEsteCalculadaLaCuota(cuota: Double)
        fun cuandoHayaErrorDeCapitalInferiorAlMinimo(capitalMinimo: Double)
        fun cuandoHayaErrorDePlazoInferiorAlMinimo(plazoMinimo: Int)
        fun cuandoEmpieceElCalculo()
        fun cuandoFinaliceElCalculo()
    }

    companion object {
        const val CAPITAL_MINIMO = 1_000.0
        const val CAPITAL_MAXIMO = 1_000_000.0
        const val PLAZO_MINIMO = 2
        const val PLAZO_MAXIMO = 40
    }

    fun calcular(solicitud: Solicitud, callback: Callback) {

        callback.cuandoEmpieceElCalculo()

        try {
            // 模拟耗时操作
            Thread.sleep(2500)

            val interesAnual = 0.01605
            val capitalMinimo = 1000.0
            val plazoMinimo = 2


            var error = false

            // 验证资本
            if (solicitud.capital < capitalMinimo) {
                callback.cuandoHayaErrorDeCapitalInferiorAlMinimo(capitalMinimo)
                error = true
            }

            // 验证期限
            if (solicitud.plazo < plazoMinimo) {
                callback.cuandoHayaErrorDePlazoInferiorAlMinimo(plazoMinimo)
                error = true
            }

            // 如果没有错误，计算月供
            if (!error) {
                val interesMensual = interesAnual / 12
                val numeroMeses = solicitud.plazo * 12.0

                // 使用标准的房贷月供计算公式
                // Cuota = Capital * (i * (1+i)^n) / ((1+i)^n - 1)
                val cuota = solicitud.capital *
                        (interesMensual * (1 + interesMensual).pow(numeroMeses)) /
                        ((1 + interesMensual).pow(numeroMeses) - 1)

                callback.cuandoEsteCalculadaLaCuota(cuota)
            }

        } catch (e: InterruptedException) {
            // 处理线程中断
            Thread.currentThread().interrupt()
        } finally {
            // 无论如何都要通知计算结束
            callback.cuandoFinaliceElCalculo()
        }
    }
}

