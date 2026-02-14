package com.example.recyclermvvm.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclermvvm.R
import com.example.recyclermvvm.databinding.FragmentElementsListBinding
import com.example.recyclermvvm.viewmodel.ElementsViewModel

class ElementsListFragment : Fragment() {

    private var _binding: FragmentElementsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ElementsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentElementsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ElementsAdapter(
            onItemClick = { element ->
                viewModel.seleccionar(element)
                findNavController().navigate(R.id.action_list_to_detail)
            },
            onRatingChanged = { element, rating ->
                viewModel.actualitzarValoracio(element, rating)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.elements.observe(viewLifecycleOwner) { llista ->
            adapter.submitList(llista)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
