package com.nbstocks.nbstocks.presentation.ui.sign_up.model

data class User(
    val uid: String? = "",
    val email: String? = "",
    val password: String? = "",
    val balance: Double? = 10000.0
)
