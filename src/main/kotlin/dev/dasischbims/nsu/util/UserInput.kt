package dev.dasischbims.nsu.util

import dev.dasischbims.nsu.util.SaveFolder.isValidSaveFolder
import java.io.File
import kotlin.system.exitProcess

object UserInput {
    fun readUserFolderInput(): File? {
        println("Do you want to specify the path to your save folder manually? (y/n)")
        return when (readlnOrNull()?.trim()?.lowercase()) {
            "y", "yes" -> {
                askForSaveFolder()
            }
            "n", "no" -> {
                println("Aborting...")
                exitProcess(0)
            }
            else -> {
                println("Invalid input")
                null
            }
        }
    }

    private fun askForSaveFolder(): File? {
        println("Please enter the path to your save folder:")
        if (readlnOrNull() == null) return null
        val userInput = readlnOrNull()?.trim()?: return null

        val userFolder = File(userInput)
        return if (userFolder.exists() && isValidSaveFolder(userFolder)) {
            println("Using user-specified folder at ${userFolder.absolutePath}")
            return userFolder
        } else {
            println("Invalid folder path: ${userFolder.absolutePath}")
            askForSaveFolder()
        }
    }
}