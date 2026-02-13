package com.example.recyclermvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclermvvm.data.model.Element
import com.example.recyclermvvm.data.repository.ElementsRepository
class ElementsViewModel : ViewModel() {

    private val repository = ElementsRepository()

    private val _elements = MutableLiveData<List<Element>>()
    val elements: LiveData<List<Element>> get() = _elements

    private val _elementSeleccionat = MutableLiveData<Element?>()
    val elementSeleccionat: LiveData<Element?> get() = _elementSeleccionat

    init {
        _elements.value = repository.obtenirElements()
    }

    fun seleccionar(element: Element) {
        _elementSeleccionat.value = element
    }

    fun actualitzarValoracio(element: Element, valoracio: Float) {
        repository.actualitzarValoracio(element, valoracio)

        _elements.value = repository.obtenirElements()
        _elementSeleccionat.value = element
    }

}

