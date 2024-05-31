package com.example.proyectofinal.data

import com.example.proyectofinal.R
import com.example.proyectofinal.model.Chevrolet
import com.example.proyectofinal.model.Ford

class DataSource {
    fun loadChevrolets(): List<Chevrolet> {
        return listOf<Chevrolet>(
            Chevrolet(R.string.camaro2024, R.drawable.camaro2024),
            Chevrolet(R.string.camaro2010, R.drawable.camaro2010),
            Chevrolet(R.string.camaro1969, R.drawable.camaro1969),
           )
    }

    fun loadFords(): List<Ford> {
        return listOf<Ford>(
            Ford(R.string.mustang2024, R.drawable.mustang2024),
            Ford(R.string.mustang2010, R.drawable.mustang2010),
            Ford(R.string.mustang1969, R.drawable.mustang1969),
        )
    }
}