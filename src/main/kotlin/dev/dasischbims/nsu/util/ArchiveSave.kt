package dev.dasischbims.nsu.util

import dev.dasischbims.nsu.util.SaveFolder.isValidSaveFolder
import org.zeroturnaround.zip.ZipUtil
import java.io.File
import java.text.SimpleDateFormat

object ArchiveSave {
    fun archiveSave(saveFolder: File) {
        if (saveFolder.exists() && isValidSaveFolder(saveFolder)) {
            val currentTime = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(System.currentTimeMillis())
            // zip the save folder
            val archiveFolder = File(saveFolder.parentFile, "Noita-Save-Archives")
            if (!archiveFolder.exists()) {
                archiveFolder.mkdir()
            }
            ZipUtil.pack(saveFolder, File(archiveFolder, "${saveFolder.name}-$currentTime.zip"))

            return println("Archived save files to ${archiveFolder.absolutePath}")
        } else {
            return println("Invalid save folder: ${saveFolder.absolutePath}")
        }
    }
}