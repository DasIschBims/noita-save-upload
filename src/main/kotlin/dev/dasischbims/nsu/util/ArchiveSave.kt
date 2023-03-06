package dev.dasischbims.nsu.util

import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import dev.dasischbims.nsu.util.SaveFolder.isValidSaveFolder
import org.zeroturnaround.zip.ZipUtil
import java.io.File
import java.text.SimpleDateFormat

object ArchiveSave {
    fun archiveSave(saveFolder: File): String {
        if (saveFolder.exists() && isValidSaveFolder(saveFolder)) {
            val currentTime = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(System.currentTimeMillis())
            val archiveName = "${saveFolder.name}-$currentTime.zip"
            // zip the save folder
            val archiveFolder = File(saveFolder.parentFile, "Noita-Save-Archives")
            if (!archiveFolder.exists()) {
                archiveFolder.mkdir()
            }
            ZipUtil.pack(saveFolder, File(archiveFolder, archiveName))

            println(Ansi.colorize("\nSuccessfully archived save files to ${archiveFolder.absolutePath}\\$archiveName\n", Attribute.GREEN_TEXT()))
            // return the archive folder path
            return "${archiveFolder.absolutePath}\\$archiveName"
        } else {
            throw IllegalArgumentException("\nInvalid save folder: ${saveFolder.absolutePath}")
        }
    }
}