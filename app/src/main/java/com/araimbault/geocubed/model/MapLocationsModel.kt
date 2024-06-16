package com.araimbault.geocubed.model

import kotlinx.serialization.Serializable

// TODO : adapt Location Response to real response

data class LocationResponse(
    val features: List<Location>
)

data class Location(
    val properties: Properties,
    val geometry: Geometry
)

data class Properties(
    val name: String,
    val country: String
    // other properties...
)


data class Geometry(
    val coordinates: List<Double>
)
