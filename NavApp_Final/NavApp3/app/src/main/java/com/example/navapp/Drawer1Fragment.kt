package com.example.navapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class Drawer1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate fragment layout
        return inflater.inflate(R.layout.fragment_drawer1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get views
        val felizNavidadText = view.findViewById<TextView>(R.id.felizNavidad)
        val button = view.findViewById<Button>(R.id.drawer1Button)

        // Button click listener
        button.setOnClickListener {
            felizNavidadText.text = "feliz navidad"
        }
    }
}
