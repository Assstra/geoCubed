package com.araimbault.geocubed.utils

fun formatDuration(durationInSeconds: Double): String {
    val hours = (durationInSeconds / 3600).toInt()
    val minutes = ((durationInSeconds % 3600) / 60).toInt()
    return when {
        hours > 0 -> "${hours} h ${minutes} min"
        else -> "${minutes}  min"
    }
}

fun formatDistance(distance: Double): String {
    val kilometers = distance / 1000.0
    return String.format("%,.2f km", kilometers)
}