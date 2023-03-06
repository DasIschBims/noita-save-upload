package dev.dasischbims.nsu

import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import dev.dasischbims.nsu.util.ArchiveSave.archiveSave
import dev.dasischbims.nsu.util.RestoreSave
import dev.dasischbims.nsu.util.SaveFolder
import dev.dasischbims.nsu.util.SaveFolder.isValidSaveFolder
import dev.dasischbims.nsu.util.UserInput.readUserFolderInput
import kotlin.system.exitProcess

// TODO: Fix up this mess of a main method. It's a mess. I know.

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(Ansi.colorize("Noita Save Uploader by DasIschBims (https://dasischbims.dev/)\n", Attribute.BLUE_TEXT()))
            try {
                val saveFolder = SaveFolder.getDefaultSaveFolder()

                if (saveFolder.exists() && isValidSaveFolder(saveFolder)) {
                    println(Ansi.colorize("Found save folder at ${saveFolder.absolutePath}\n", Attribute.WHITE_TEXT()))
                } else {
                    println("Could not find save folder at ${saveFolder.absolutePath}\n")
                    while (true) {
                        val userInput = readUserFolderInput()
                        if (userInput != null) {
                            if (userInput.exists() && isValidSaveFolder(userInput)) {
                                println("Using user-specified folder at ${userInput.absolutePath}\n")
                                break
                            } else {
                                println("Invalid folder path: ${userInput.absolutePath}\n")
                            }
                        }
                    }
                }

                println(Ansi.colorize("Pick an action: ", Attribute.BLUE_TEXT()))
                println(Ansi.colorize("(1) Restore save files", Attribute.YELLOW_TEXT()))
                println(Ansi.colorize("(2) Archive save files", Attribute.YELLOW_TEXT()))
                println(Ansi.colorize("(3) Exit\n", Attribute.YELLOW_TEXT()))
                when (readlnOrNull()?.trim()?.lowercase()) {
                    "1", "restore" -> {
                        println(Ansi.colorize("Proceed with caution!\nThis will override your current save files with the ones from the backup.\nProceed? (y/n)\n", Attribute.RED_TEXT()))
                        when (readlnOrNull()?.trim()?.lowercase()) {
                            "y", "yes" -> {
                                println(Ansi.colorize("\nAre you sure? (y/n)\n", Attribute.RED_TEXT()))
                                when (readlnOrNull()?.trim()?.lowercase()) {
                                    "y", "yes" -> {
                                        println(Ansi.colorize("\nRestoring save files...", Attribute.WHITE_TEXT()))
                                        val saveFiles = RestoreSave.getLatestSaveFiles(saveFolder)
                                        RestoreSave.restoreSaveFiles(saveFiles, saveFolder)
                                        println(Ansi.colorize("Successfully restored save files!\nHappy Noiting! :)", Attribute.GREEN_TEXT()))
                                        exitProcess(0)
                                    }
                                    "n", "no" -> {
                                        println(Ansi.colorize("Aborting...", Attribute.WHITE_TEXT()))
                                        exitProcess(0)
                                    }
                                    else -> {
                                        println(Ansi.colorize("Invalid input", Attribute.RED_TEXT()))
                                    }
                                }
                            }

                            "n", "no" -> {
                                println(Ansi.colorize("Aborting...", Attribute.WHITE_TEXT()))
                                exitProcess(0)
                            }

                            else -> {
                                println(Ansi.colorize("Invalid input", Attribute.RED_TEXT()))
                            }
                        }
                    }
                    "2", "archive" -> {
                        archiveSave(saveFolder)
                        // println("Do you also want to upload the save files to the cloud (google drive)? (y/n)")
                        //                        when (readlnOrNull()?.trim()?.lowercase()) {
                        //                            "y", "yes" -> {
                        //                                println("This is not yet implemented, sorry :(")
                        //                            }
                        //                            "n", "no" -> {
                        //                                println("Aborting...")
                        //                                exitProcess(0)
                        //                            }
                        //                            else -> {
                        //                                println("Invalid input")
                        //                            }
                        //                        }
                    }
                    "3", "exit" -> {
                        println(Ansi.colorize("Aborting...", Attribute.WHITE_TEXT()))
                        exitProcess(0)
                    }
                    else -> {
                        println(Ansi.colorize("Invalid input", Attribute.RED_TEXT()))
                    }
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
                exitProcess(1)
            }
        }
    }
}