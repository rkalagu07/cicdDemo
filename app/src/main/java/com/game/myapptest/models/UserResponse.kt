package com.game.myapptest.models

import com.game.myapptest.models.User

data class UserResponse(
    val token: String,
    val user: User
)