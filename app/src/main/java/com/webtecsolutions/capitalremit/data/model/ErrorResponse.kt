package com.webtecsolutions.capitalremit.data.model

data class ErrorResponse(
    val code: Int,
    val message: String,
    val status: Boolean
)