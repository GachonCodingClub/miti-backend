package com.gcc.miti.user.dto

import com.gcc.miti.user.constants.Height
import com.gcc.miti.user.constants.Weight

class UpdateProfileRequest (
    val nickname: String,
    val height: Height,
    val weight: Weight,
    val description: String?
)