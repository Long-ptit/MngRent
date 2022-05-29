package com.restaurant.exam.data.model

import java.io.Serializable

data class Restaurant(
    var id: Int? = null,
    var username: String? = null,
    var password: String? = null,
    var name: String? = null,
    var phone: String? = null,
    var address: String? = null,
) : Serializable
