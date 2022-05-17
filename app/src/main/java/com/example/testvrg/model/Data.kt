package com.example.testvrg.model

data class Data(
    val after: String,
    val children: List<Children>,
    val dist: Int,
    val geo_filter: String,
    val modhash: String,
    val before: String
)