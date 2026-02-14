package com.example.recyclermvvm.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.recyclermvvm.databinding.FragmentElementDetailBinding
import com.example.recyclermvvm.viewmodel.ElementsViewModel

class ElementDetailFragment : Fragment() {

    private var _binding: FragmentElementDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ElementsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentElementDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.elementSeleccionat.observe(viewLifecycleOwner) { element ->

            if (element != null) {

                binding.textNom.text = element.nom

                binding.textDescripcio.text = element.descripcio

                binding.ratingValoracio.rating = element.valoracio

                binding.textValoracio.text = "${element.valoracio} / 5"

                binding.ratingValoracio.isEnabled = true

                binding.ratingValoracio.setOnRatingBarChangeListener { _, rating, fromUser ->
                    if (fromUser) {
                        viewModel.actualitzarValoracio(element, rating)
                        binding.textValoracio.text = "$rating / 5"
                    }
                }
            } else {
                binding.ratingValoracio.isEnabled = false
            }
        }

        binding.btnTornar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
