package com.nevoit.filo.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import filo.composeapp.generated.resources.Res
import filo.composeapp.generated.resources.SF_Pro
import org.jetbrains.compose.resources.Font


val SfFontFamily
    @Composable
    get() = FontFamily(Font(Res.font.SF_Pro))


@Composable
fun Symbol(
    icon: String,
    size: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current
) {
    val density = LocalDensity.current

    val fontSizeSp = with(density) { (size / 2).toSp() }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = icon,
            fontFamily = SfFontFamily,
            fontSize = fontSizeSp,
            lineHeight = fontSizeSp,
            color = color,
            textAlign = TextAlign.Center
        )
    }
}