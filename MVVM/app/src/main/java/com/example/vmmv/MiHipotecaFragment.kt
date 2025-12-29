package com.example.vmmv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vmmv.databinding.FragmentMiHipotecaBinding

class MiHipotecaFragment : Fragment() {

    private var _binding: FragmentMiHipotecaBinding? = null
    private val binding get() = _binding!!
    private lateinit var miHipotecaViewModel: MiHipotecaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMiHipotecaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        miHipotecaViewModel = ViewModelProvider(this)[MiHipotecaViewModel::class.java]

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        // 清除错误信息当用户开始输入
        binding.capital.addTextChangedListener {
            binding.capital.error = null
        }

        binding.plazo.addTextChangedListener {
            binding.plazo.error = null
        }

        binding.calcular.setOnClickListener {
            val capitalText = binding.capital.text.toString()
            val plazoText = binding.plazo.text.toString()

            // 验证输入
            if (!validateInputs(capitalText, plazoText)) {
                return@setOnClickListener
            }

            try {
                val capital = capitalText.toDouble()
                val plazo = plazoText.toInt()

                // 清除之前的结果
                binding.cuota.text = ""
                binding.errorMessage.visibility = View.GONE

                miHipotecaViewModel.calcular(capital, plazo)
            } catch (e: NumberFormatException) {
                showError("Por favor ingrese valores numéricos válidos")
            }
        }
    }

    private fun validateInputs(capitalText: String, plazoText: String): Boolean {
        var isValid = true

        if (capitalText.isEmpty()) {
            binding.capital.error = "El capital es obligatorio"
            isValid = false
        } else {
            try {
                val capital = capitalText.toDouble()
                if (capital <= 0) {
                    binding.capital.error = "El capital debe ser mayor que 0"
                    isValid = false
                }
            } catch (e: NumberFormatException) {
                binding.capital.error = "Ingrese un número válido"
                isValid = false
            }
        }

        if (plazoText.isEmpty()) {
            binding.plazo.error = "El plazo es obligatorio"
            isValid = false
        } else {
            try {
                val plazo = plazoText.toInt()
                if (plazo <= 0) {
                    binding.plazo.error = "El plazo debe ser mayor que 0"
                    isValid = false
                }
            } catch (e: NumberFormatException) {
                binding.plazo.error = "Ingrese un número entero válido"
                isValid = false
            }
        }

        return isValid
    }

    private fun setupObservers() {
        miHipotecaViewModel.cuota.observe(viewLifecycleOwner) { cuota ->
            binding.cuota.text = String.format("%.2f €/mes", cuota)
            binding.cuota.visibility = View.VISIBLE
            binding.errorMessage.visibility = View.GONE
        }

        miHipotecaViewModel.calculando.observe(viewLifecycleOwner) { calculando ->
            if (calculando) {
                binding.progressBar.visibility = View.VISIBLE
                binding.progressText.visibility = View.VISIBLE
                binding.cuota.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE

                binding.calcular.isEnabled = false
                binding.capital.isEnabled = false
                binding.plazo.isEnabled = false
            } else {
                binding.progressBar.visibility = View.GONE
                binding.progressText.visibility = View.GONE

                binding.calcular.isEnabled = true
                binding.capital.isEnabled = true
                binding.plazo.isEnabled = true
            }
        }

        // 观察错误
        miHipotecaViewModel.errorCapital.observe(viewLifecycleOwner) { capitalMinimo ->
            capitalMinimo?.let {
                val mensaje = "El capital mínimo permitido es ${String.format("%.2f", it)} €"
                binding.capital.error = mensaje
                showError(mensaje)
            }
        }

        miHipotecaViewModel.errorPlazos.observe(viewLifecycleOwner) { plazoMinimo ->
            plazoMinimo?.let {
                val mensaje = "El plazo mínimo permitido es $it años"
                binding.plazo.error = mensaje
                showError(mensaje)
            }
        }
    }

    private fun showError(mensaje: String) {
        binding.errorMessage.text = mensaje
        binding.errorMessage.visibility = View.VISIBLE
        binding.cuota.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}