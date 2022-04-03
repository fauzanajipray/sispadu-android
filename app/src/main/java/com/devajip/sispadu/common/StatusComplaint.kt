package com.devajip.sispadu.common

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.devajip.sispadu.R
import com.devajip.sispadu.presentation.theme.Orange300

data class StatusComplaint(
    val status: String = Constant.STATUS_PENDING,
    val message: String = "",
    @DrawableRes val icon: Int = R.drawable.ic_pending,
    val color: Color = Orange300
)
