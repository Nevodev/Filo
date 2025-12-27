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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mocharealm.gaze.capsule.ContinuousRoundedRectangle
import com.nevoit.filo.component.Symbol
import com.nevoit.filo.theme.GlasenseTheme
import org.jetbrains.jewel.ui.component.Text

@Composable
fun Sidebar(modifier: Modifier = Modifier) {
    val contentColor = LocalContentColor.current
    Box(
        modifier = modifier.zIndex(100f)
            .width(256.dp)
            .fillMaxHeight()
            .background(color = GlasenseTheme.colorScheme.background)
            .drawBehind {
                drawRect(
                    color = contentColor.copy(.1f),
                    size = Size(1.dp.toPx(), this.size.height),
                    topLeft = Offset(this.size.width, 0f)
                )
            }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(top = 40.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                SidebarItem(
                    icon = "\uDBC1\uDF0D",
                    text = "整理",
                    isSelected = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                SidebarItem(
                    icon = "\uDBC0\uDE15",
                    text = "文件夹",
                    isSelected = false
                )
                Spacer(modifier = Modifier.height(8.dp))
                SidebarItem(
                    icon = "\uDBC0\uDEE1",
                    text = "标签",
                    isSelected = false
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Column() {
                SidebarItem(
                    icon = "\uDBC0\uDF5F",
                    text = "设置",
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
            .then(
                if (isSelected) Modifier.dropShadow(
                    shape = ContinuousRoundedRectangle(8.dp), shadow = Shadow(
                        radius = 8.dp,
                        offset = DpOffset(0.dp, 4.dp),
                        color = Color.Black,
                        alpha = .1f
                    )
                ) else Modifier
            )
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