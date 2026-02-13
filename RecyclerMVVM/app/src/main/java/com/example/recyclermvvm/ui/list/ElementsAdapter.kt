package com.example.recyclermvvm.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclermvvm.databinding.ItemElementBinding
import com.example.recyclermvvm.data.model.Element

class ElementsAdapter(

    private val onItemClick: (Element) -> Unit,

    private val onRatingChanged: (Element, Float) -> Unit

) : RecyclerView.Adapter<ElementsAdapter.ViewHolder>() {

    private var llista: List<Element> = emptyList()

    fun submitList(novaLlista: List<Element>) {
        llista = novaLlista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemElementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = llista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(llista[position])
    }


    inner class ViewHolder(
        private val binding: ItemElementBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(element: Element) {

            binding.nom.text = element.nom

            binding.valoracio.rating = element.valoracio

            binding.root.setOnClickListener {
                onItemClick(element)
            }

            binding.valoracio.setOnRatingBarChangeListener { _, rating, fromUser ->
                if (fromUser) {
                    onRatingChanged(element, rating)
                }
            }
        }
    }
}

