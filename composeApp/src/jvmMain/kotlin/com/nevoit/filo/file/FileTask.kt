package com.nevoit.filo.file

import androidx.compose.runtime.Immutable
import java.io.File

enum class ProcessStatus {
    WAITING, PROCESSING, COMPLETED, ERROR
}

@Immutable
data class FileTask(
    val file: File,
    val status: ProcessStatus = ProcessStatus.WAITING,
    val tags: List<String> = emptyList()
)