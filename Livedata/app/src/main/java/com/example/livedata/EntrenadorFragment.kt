package com.example.livedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.livedata.databinding.FragmentEntrenadorBinding

class EntrenadorFragment : Fragment() {

    private var _binding: FragmentEntrenadorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntrenadorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entrenadorViewModel = ViewModelProvider(this)[EntrenadorViewModel::class.java]

        entrenadorViewModel.ejercicioLiveData.observe(viewLifecycleOwner) { ejercicio ->
            ejercicio?.let {
                Glide.with(this@EntrenadorFragment)
                    .load(it)
                    .into(binding.ejercicio)
            }
        }

        entrenadorViewModel.repeticionLiveData.observe(viewLifecycleOwner) { repeticion ->
            if (repeticion == "CAMBIO") {
                binding.cambio.visibility = View.VISIBLE
            } else {
                binding.cambio.visibility = View.GONE
            }
            binding.repeticion.text = repeticion
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}