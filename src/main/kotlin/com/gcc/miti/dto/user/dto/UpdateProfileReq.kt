package com.gcc.miti.dto.user.dto

import com.gcc.miti.constants.Height
import com.gcc.miti.constants.Weight

class UpdateProfileReq (
    val nickname: String,
    val height: Height,
    val weight: Weight,
    val description: String?
)