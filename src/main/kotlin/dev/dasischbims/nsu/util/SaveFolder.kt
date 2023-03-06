package dev.dasischbims.nsu.util

import java.io.File

object SaveFolder {
    fun getDefaultSaveFolder(): File {
        val os = System.getProperty("os.name").lowercase()
        return when {
            os.contains("windows") -> {
                // get appdata folder
                val userProfile = System.getenv("USERPROFILE")
                File("$userProfile\\AppData\\LocalLow\\Nolla_Games_Noita")
            }
            os.contains("linux") -> {
                val home = System.getProperty("user.home")
                File("$home/.local/share/Steam/steamapps/compatdata/881100/pfx/drive_c/users/steamuser/AppData/LocalLow/Nolla_Games_Noita")
            }
            else -> throw UnsupportedOperationException("Unsupported operating system")
        }
    }

    fun isValidSaveFolder(folder: File): Boolean {
        val exists = folder.exists()
        val isDirectory = folder.isDirectory
        val fileList = folder.listFiles()
        val containsSaveFile = fileList?.any { it.name == "save00" } ?: false
        return exists && isDirectory && containsSaveFile
    }
}