package dev.dasischbims.nsu.util

import java.io.File

object SaveFolder {
    fun getDefaultSaveFolder(): File {
        return when (System.getProperty("os.name").lowercase()) {
            "windows" -> {
                val appData = System.getenv("LOCALAPPDATA")
                File("$appData\\LocalLow\\Nolla_Games_Noita")
            }
            "linux" -> {
                val steamUser = "steamuser" // replace this with your actual Steam username
                val home = System.getProperty("user.home")
                File("$home/.local/share/Steam/steamapps/compatdata/881100/pfx/drive_c/users/$steamUser/AppData/LocalLow/Nolla_Games_Noita")
            }
            else -> throw UnsupportedOperationException("Unsupported operating system")
        }
    }

    fun isValidSaveFolder(folder: File): Boolean {
        return folder.exists() && folder.isDirectory && folder.listFiles()?.any { it.name == "save00" } ?: false
    }
}