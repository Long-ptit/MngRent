package com.restaurant.exam.data.model

import java.util.Date

data class Bill(
var id: Int? = null,
var restaurant: Restaurant? = null,
var staff: Staff? = null,
var dateNew: String? = null,
var totalPrice: Int? = null,
var quantity: Int? = null,
var idBan: String? = null
)
