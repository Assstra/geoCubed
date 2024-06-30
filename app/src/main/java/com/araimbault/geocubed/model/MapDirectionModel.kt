package com.araimbault.geocubed.model

data class DirectionResponse(
    val type: String,
    val bbox: List<Double>,
    val features: List<Feature>,
    val metadata: Metadata
)

data class Feature(
    val bbox: List<Double>,
    val type: String,
    val properties: DirectionProperties,
    val geometry: DirectionGeometry
)

data class DirectionProperties(
    val segments: List<Segment>,
    val way_points: List<Int>,
    val summary: Summary
)

data class Segment(
    val distance: Double,
    val duration: Double,
    val steps: List<Step>
)

data class Step(
    val distance: Double,
    val duration: Double,
    val type: Int,
    val instruction: String,
    val name: String,
    val way_points: List<Int>
)

data class Summary(
    val distance: Double,
    val duration: Double
)

data class DirectionGeometry(
    val coordinates: List<List<Double>>,
    val type: String
)

data class Metadata(
    val attribution: String,
    val service: String,
    val timestamp: Long,
    val query: Query,
)


data class Query(
    val coordinates: List<List<Double>>,
    val profile: String,
    val format: String
)

