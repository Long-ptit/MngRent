package com.restaurant.exam.data.model

data class TableRestaurant(
    var id: Int? = null,
    var name: String? = null,
    var floor: Floor? = null,
    var isSelected: Boolean = false,
)