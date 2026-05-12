package com.example.weatherapp.modules.viewmodel.search.model

data class SearchResponse(
    val results: List<SearchResult>? = null
)

data class SearchResult(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String? = null,
    val admin1: String? = null
)
