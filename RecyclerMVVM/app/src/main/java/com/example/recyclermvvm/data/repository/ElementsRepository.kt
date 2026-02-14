package com.example.recyclermvvm.data.repository

import com.example.recyclermvvm.data.model.Element

class ElementsRepository {

    private val elements = mutableListOf<Element>()

    private var nextId = 1

    init {
        inserir(
            Element(
                id = nextId++,
                nom = "Element químic",
                descripcio = "Àtom que no es pot descompondre",
                categoria = "Ciència"
            )
        )
        inserir(
            Element(
                id = nextId++,
                nom = "Element matemàtic",
                descripcio = "Membre d’un conjunt",
                categoria = "Matemàtiques"
            )
        )
        inserir(
            Element(
                id = nextId++,
                nom = "Element natural",
                descripcio = "Aigua, foc, aire i terra",
                categoria = "Natura"
            )
        )
    }

    fun obtenirElements(): List<Element> {
        return elements.toList()
    }


    fun inserir(element: Element) {
        val elementAmbId =
            if (element.id <= 0) {
                element.copy(id = nextId++)
            } else {
                element
            }

        elements.add(elementAmbId)
    }

    fun actualitzarValoracio(element: Element, novaValoracio: Float) {
        elements.find { it.id == element.id }?.let {
            it.valoracio = novaValoracio
        }
    }
}
