package dev.dasischbims.nsu.util

import dev.dasischbims.nsu.util.SaveFolder.isValidSaveFolder
import java.io.File
import java.text.SimpleDateFormat

object ArchiveSave {
    // TODO: Use this to archive save files
    fun archiveSave(saveFolder: File) {
        if (saveFolder.exists() && isValidSaveFolder(saveFolder)) {
            val currentTime = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())
            val archiveFolder = File(saveFolder, "NoitaSave${currentTime}")
            if (!archiveFolder.exists()) archiveFolder.mkdir()

            saveFolder.listFiles()?.forEach { file ->
                if (file.name.startsWith("save")) {
                    file.copyTo(File(archiveFolder, file.name), true)
                }
            }

            println("Archived save files to ${archiveFolder.absolutePath}")
        } else {
            println("Invalid save folder: ${saveFolder.absolutePath}")
            return
        }
    }
}