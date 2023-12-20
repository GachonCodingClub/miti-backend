package com.gcc.miti.module.dto.user.dto

import com.gcc.miti.module.constants.Height
import com.gcc.miti.module.constants.Weight

class UpdateProfileReq (
    val nickname: String,
    val height: Height,
    val weight: Weight,
    val description: String?
)