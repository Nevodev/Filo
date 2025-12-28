package com.nevoit.filo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nevoit.filo.component.Text
import com.nevoit.filo.data.FileRepository
import com.nevoit.filo.data.VirtualFile
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberDirectoryPickerLauncher
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.swing.JFileChooser
import javax.swing.UIManager

@Composable
fun OrganizeScreen() {
    val scope = rememberCoroutineScope()

    var selectedCategory by remember { mutableStateOf("All") }
    var fileList by remember { mutableStateOf(emptyList<VirtualFile>()) }

    fun loadFiles() {
        scope.launch {
            fileList = if (selectedCategory == "All") {
                FileRepository.getAllFiles()
            } else {
                FileRepository.getFilesByCategory(selectedCategory)
            }
        }
    }

    val filePicker = rememberFilePickerLauncher(
        type = FileKitType.File(),
        mode = FileKitMode.Multiple(),
        title = "导入文件"
    ) { files ->
        if (!files.isNullOrEmpty()) {
            scope.launch {
                val javaFiles = files.map { it.file }
                FileRepository.addFilesBatch(javaFiles, "Work")
                loadFiles()
            }
        }
    }

    val folderPicker = rememberDirectoryPickerLauncher(
        title = "导入文件夹"
    ) { folder ->
        if (folder != null) {
            scope.launch {
                val javaFolder = folder.file
                // 扫描文件夹逻辑
                val filesInFolder = withContext(Dispatchers.IO) {
                    javaFolder.walk().filter { it.isFile }.toList()
                }
                FileRepository.addFilesBatch(filesInFolder, "Work")
                loadFiles()
            }
        }
    }

    LaunchedEffect(selectedCategory) {
        loadFiles()
    }

    Row(modifier = Modifier.fillMaxSize().padding(top = 40.dp)) {
        Column(modifier = Modifier.width(200.dp).fillMaxHeight().background(Color(0xFFF0F0F0))) {
            listOf("All", "Work", "Personal").forEach { cat ->
                SidebarItem(cat, selectedCategory) { selectedCategory = cat }
            }
        }

        Column(modifier = Modifier.weight(1f).padding(16.dp)) {
            Button(onClick = { filePicker.launch() }) {
                Text("导入文件")
            }
            
            Button(onClick = { folderPicker.launch() }) {
                Text("导入文件夹")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(fileList) { file ->
                    FileRow(
                        file = file,
                        onDelete = {
                            scope.launch {
                                FileRepository.deleteFile(file.id)
                                loadFiles()
                            }
                        }
                    )
                }
            }
        }
    }
//    Box(modifier = Modifier.fillMaxSize()) {
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(start = 12.dp, top = 40.dp, end = 12.dp)
//        ) {
//            item {
//                Box(modifier = Modifier.fillMaxWidth()) {
//                    Row(modifier = Modifier.align(Alignment.Center)) {
//                        Text(
//                            text = "拖放文件，",
//                            fontSize = 24.sp,
//                            color = LocalContentColor.current.copy(.5f)
//                        )
//                        Text("开始整理", fontSize = 24.sp)
//                    }
//                }
//                Spacer(Modifier.height(12.dp))
//            }
//            item {
//                DropZone { }
//            }
//        }
//    }
}

@Composable
fun FileRow(file: VirtualFile, onDelete: () -> Unit) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(),
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(file.name)
                Text(file.path)
            }
            // 删除按钮
            IconButton(onClick = onDelete) {
            }
        }
    }
}

@Composable
fun SidebarItem(name: String, current: String, onClick: () -> Unit) {
    val isSelected = name == current
    Text(
        text = name,
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isSelected) Color.LightGray else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(16.dp)
    )
}

fun openFileOrFolderDialog(): List<File> {
    // 设置让 Swing 控件长得像系统原生控件 (否则很丑)
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    } catch (e: Exception) {
        e.printStackTrace()
    }

    val chooser = JFileChooser()
    // 关键设置：允许选择文件 AND 文件夹
    chooser.fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES
    // 关键设置：允许同时选多个
    chooser.isMultiSelectionEnabled = true
    chooser.dialogTitle = "选择文件或文件夹"

    val result = chooser.showOpenDialog(null)

    return if (result == JFileChooser.APPROVE_OPTION) {
        // 获取所有选中的对象
        chooser.selectedFiles.toList()
    } else {
        emptyList()
    }
}