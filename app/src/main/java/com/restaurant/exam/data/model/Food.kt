package com.restaurant.exam.data.model

import java.io.Serializable

data class Food (
    var id: Int? = null,
    var name: String? = null,
    var quantity: Int = 0,
    var price: Int = 0,
    var url: String? = null,
    var isSelected: Boolean = false,
    var foodCategory: Category? = null
) : Serializable