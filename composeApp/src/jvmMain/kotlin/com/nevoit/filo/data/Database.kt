package com.nevoit.filo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.number
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.io.File

data class VirtualFile(
    val id: Int,
    val path: String,
    val name: String,
    val category: String,
    val addedAt: String
)

object FileTable : Table("files") {
    val id = integer("id").autoIncrement()
    val filePath = varchar("file_path", 500)
    val fileName = varchar("file_name", 255)
    val category = varchar("category", 50)

    val addedAt = datetime("added_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}

object FileRepository {
    fun init() {
        Database.connect("jdbc:sqlite:my_files.db", "org.sqlite.JDBC")

        transaction {
            SchemaUtils.create(FileTable)
        }
    }

    private suspend fun <T> dbQuery(block: suspend Transaction.() -> T): T =
        withContext(Dispatchers.IO) {
            suspendTransaction { block() }
        }

    suspend fun addFile(file: File, categoryStr: String) = dbQuery {
        val count = FileTable.selectAll()
            .where { FileTable.filePath eq file.absolutePath }
            .count()

        if (count == 0L) {
            FileTable.insert {
                it[filePath] = file.absolutePath
                it[fileName] = file.name
                it[category] = categoryStr
            }
        } else {
            println("file already added: ${file.name}")
        }
    }

    suspend fun addFilesBatch(files: List<File>, categoryStr: String) = dbQuery {
        files.forEach { file ->
            val count = FileTable.selectAll()
                .where { FileTable.filePath eq file.absolutePath }
                .count()

            if (count == 0L) {
                FileTable.insert {
                    it[filePath] = file.absolutePath
                    it[fileName] = file.name
                    it[category] = categoryStr
                }
            }
        }
    }
    
    suspend fun getAllFiles(): List<VirtualFile> = dbQuery {
        FileTable.selectAll()
            .orderBy(FileTable.addedAt, SortOrder.DESC)
            .map { it.toVirtualFile() }
    }

    suspend fun getFilesByCategory(cat: String): List<VirtualFile> = dbQuery {
        FileTable.selectAll()
            .where { FileTable.category eq cat }
            .orderBy(FileTable.addedAt, SortOrder.DESC)
            .map { it.toVirtualFile() }
    }

    private fun ResultRow.toVirtualFile(): VirtualFile {
        val date = this[FileTable.addedAt]
        val dateStr =
            "${date.year}-${date.month.number}-${date.day} ${date.hour}:${date.minute}"

        return VirtualFile(
            id = this[FileTable.id],
            path = this[FileTable.filePath],
            name = this[FileTable.fileName],
            category = this[FileTable.category],
            addedAt = dateStr
        )
    }

    suspend fun deleteFile(id: Int) = dbQuery {
        FileTable.deleteWhere { FileTable.id eq id }
    }
}