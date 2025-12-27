package com.nevoit.filo.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mocharealm.gaze.capsule.ContinuousRoundedRectangle
import com.nevoit.filo.component.Symbol
import com.nevoit.filo.theme.GlasenseTheme
import org.jetbrains.jewel.ui.component.Text

@Composable
fun Sidebar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(256.dp)
            .fillMaxHeight()
            .background(color = GlasenseTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                SidebarItem(
                    icon = "\uDBC0\uDF9E",
                    text = "Home",
                    isSelected = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                SidebarItem(
                    icon = "\uDBC0\uDF9E",
                    text = "Home",
                    isSelected = false
                )
                Spacer(modifier = Modifier.height(8.dp))
                SidebarItem(
                    icon = "\uDBC0\uDF9E",
                    text = "Home",
                    isSelected = false
                )
                Spacer(modifier = Modifier.height(8.dp))
                SidebarItem(
                    icon = "\uDBC0\uDF9E",
                    text = "Home",
                    isSelected = false
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Column() {
                SidebarItem(
                    icon = "\uDBC0\uDF5F",
                    text = "Settings",
                    endText = "2000",
                    isSelected = false
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}

@Composable
fun SidebarItem(
    modifier: Modifier = Modifier,
    icon: String = "",
    text: String,
    endText: String = "",
    isSelected: Boolean = false
) {
    val color = if (isSelected) GlasenseTheme.colorScheme.primary else LocalContentColor.current

    Row(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .height(40.dp)
            .background(
                color = if (isSelected) GlasenseTheme.colorScheme.surface else Color.Transparent,
                shape = ContinuousRoundedRectangle(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        Symbol(
            icon = icon,
            size = 32.dp,
            color = color
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, color = color, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(4.dp))
        if (endText.isNotBlank()) {
            Text(text = endText, color = LocalContentColor.current.copy(.5f))
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}