package com.restaurant.exam.data.model

import java.io.Serializable

data class TableFirebase (
    var id: Int? = null,
    var name: String? = null,
    var status: String? = null,
) : Serializable