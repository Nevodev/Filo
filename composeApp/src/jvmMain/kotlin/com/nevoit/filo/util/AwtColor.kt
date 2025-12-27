package com.nevoit.filo.util

import androidx.compose.ui.graphics.Color
import java.awt.Color as AwtColor

fun Color.toAwtColor(): AwtColor {
    return AwtColor(
        this.red,
        this.green,
        this.blue,
        this.alpha
    )
}