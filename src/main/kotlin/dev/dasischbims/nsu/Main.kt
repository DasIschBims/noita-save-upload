package dev.dasischbims.nsu

import dev.dasischbims.nsu.util.SaveFolder
import dev.dasischbims.nsu.util.SaveFolder.isValidSaveFolder
import dev.dasischbims.nsu.util.UserInput.readUserFolderInput

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val saveFolder = SaveFolder.getDefaultSaveFolder()

            if (saveFolder.exists() && isValidSaveFolder(saveFolder)) {
                println("Found save folder at ${saveFolder.absolutePath}")
            } else {
                println("Could not find save folder at ${saveFolder.absolutePath}")
                while (true) {
                    val userInput = readUserFolderInput()
                    if (userInput != null) {
                        if (userInput.exists() && isValidSaveFolder(userInput)) {
                            println("Using user-specified folder at ${userInput.absolutePath}")
                            break
                        } else {
                            println("Invalid folder path: ${userInput.absolutePath}")
                        }
                    }
                }
            }
        }
    }
}