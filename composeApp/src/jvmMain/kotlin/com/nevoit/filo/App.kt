package com.nevoit.filo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nevoit.filo.layout.Sidebar
import com.nevoit.filo.screen.OrganizeScreen
import com.nevoit.filo.theme.GlasenseTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    GlasenseTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            Sidebar()
            Box(
                modifier = Modifier.weight(1f).fillMaxHeight()
                    .background(color = GlasenseTheme.colorScheme.background)
            ) {
                OrganizeScreen()
            }
        }
    }
}