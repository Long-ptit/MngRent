package com.restaurant.exam.data.model

import java.io.Serializable

data class Category(
    var id: Int? = null,
    var name: String? = null,
) : Serializable