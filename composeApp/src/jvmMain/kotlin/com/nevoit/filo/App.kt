package com.nevoit.filo

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nevoit.filo.layout.Sidebar
import com.nevoit.filo.theme.GlasenseTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    GlasenseTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            Sidebar()
        }
    }
}