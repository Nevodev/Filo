package com.nevoit.filo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.nevoit.filo.component.Text
import com.nevoit.filo.theme.GlasenseTheme
import com.nevoit.filo.util.toAwtColor
import filo.composeapp.generated.resources.Res
import filo.composeapp.generated.resources.filo_icon_96
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.intui.standalone.theme.createDefaultTextStyle
import org.jetbrains.jewel.intui.standalone.theme.createEditorTextStyle
import org.jetbrains.jewel.intui.standalone.theme.darkThemeDefinition
import org.jetbrains.jewel.intui.standalone.theme.default
import org.jetbrains.jewel.intui.standalone.theme.lightThemeDefinition
import org.jetbrains.jewel.intui.window.decoratedWindow
import org.jetbrains.jewel.intui.window.styling.dark
import org.jetbrains.jewel.intui.window.styling.lightWithLightHeader
import org.jetbrains.jewel.ui.ComponentStyling
import org.jetbrains.jewel.window.DecoratedWindow
import org.jetbrains.jewel.window.TitleBar
import org.jetbrains.jewel.window.styling.TitleBarColors
import org.jetbrains.jewel.window.styling.TitleBarStyle

fun main() = application {
    GlasenseTheme {
        val textStyle = JewelTheme.createDefaultTextStyle()
        val editorStyle = JewelTheme.createEditorTextStyle()

        val themeDefinition =
            if (GlasenseTheme.colorScheme.isDark) {
                JewelTheme.darkThemeDefinition(
                    defaultTextStyle = textStyle,
                    editorTextStyle = editorStyle
                )
            } else {
                JewelTheme.lightThemeDefinition(
                    defaultTextStyle = textStyle,
                    editorTextStyle = editorStyle
                )
            }

        val darkColors = TitleBarColors.dark(
            backgroundColor = Color.Transparent,
            inactiveBackground = Color.Transparent,
            contentColor = Color.White
        )
        val lightColors = TitleBarColors.lightWithLightHeader(
            backgroundColor = Color.White.copy(.0000000001f), // if you don't do this, icon pane will be white, I don't fucking know why
            inactiveBackground = Color.Transparent,
            contentColor = Color.Black
        )

        IntUiTheme(
            theme = themeDefinition,
            styling = ComponentStyling.default().decoratedWindow(
                titleBarStyle = when (GlasenseTheme.colorScheme.isDark) {
                    true -> TitleBarStyle.dark(colors = darkColors)
                    false -> TitleBarStyle.lightWithLightHeader(colors = lightColors)
                }
            ),
        ) {
            val icon = painterResource(Res.drawable.filo_icon_96)

            val windowState = rememberWindowState(
                size = DpSize(width = 1024.dp, height = 720.dp),
                position = WindowPosition.PlatformDefault
            )

            DecoratedWindow(
                onCloseRequest = ::exitApplication,
                title = "Filo",
                icon = icon,
                state = windowState
            ) {
                window.background = GlasenseTheme.colorScheme.background.toAwtColor()
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = GlasenseTheme.colorScheme.background)
                ) {
                    App()
                    WindowDraggableArea {
                        TitleBar {
                            Row(
                                modifier = Modifier.align(Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Filo", modifier = Modifier
                                        .padding(start = 12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}