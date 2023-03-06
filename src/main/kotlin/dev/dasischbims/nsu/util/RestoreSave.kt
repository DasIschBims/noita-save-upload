package dev.dasischbims.nsu.util

import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.zeroturnaround.zip.ZipUtil
import java.io.File

object RestoreSave {
    fun getLatestSaveFiles(saveFolder: File): File {
        val backupFolder = File(saveFolder.parentFile, "Noita-Save-Archives")
        if (backupFolder.exists()) {
            val backupFiles = backupFolder.listFiles()
            if (backupFiles != null) {
                val latestBackupFile = backupFiles.maxByOrNull { it.lastModified() }
                if (latestBackupFile != null) {
                    println(Ansi.colorize("Found latest backup file at ${latestBackupFile.absolutePath}\n", Attribute.WHITE_TEXT()))
                    return latestBackupFile
                }
            }
        }
        throw IllegalArgumentException("Could not find latest backup file\n\nAborting...")
    }

    fun restoreSaveFiles(saveFiles: File, saveFolder: File) {
        ZipUtil.unpack(saveFiles, saveFolder)
    }
}