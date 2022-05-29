package com.restaurant.exam.data.model

import java.io.Serializable

data class Staff(
    var id: Int? = null,
    var username: String? = null,
    var password: String? = null,
    var role: String? = null,
    var name: String? = null,
    var address: String? = null,
    var phone: String? = null,
    var idManage: Int = 0,
    var restaurant: Restaurant? = null
) : Serializable
