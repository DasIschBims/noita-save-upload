package dev.dasischbims.nsu.gdrive

import com.google.api.client.http.FileContent
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials

object GoogleDriverUploader {
    fun uploadZipFile(file: java.io.File) {
        if (file.exists() && file.extension == "zip") {
            try {
                val scopes = listOf(DriveScopes.DRIVE)
                val credentials = GoogleCredentials.fromStream(javaClass.getResourceAsStream("/credentials.json"))
                    .createScoped(scopes)
                val requestInitializer = HttpRequestInitializer { request ->
                    val credentialsAdapter = HttpCredentialsAdapter(credentials)
                    credentialsAdapter.initialize(request)
                    request.connectTimeout = 60000
                    request.readTimeout = 60000
                }
                val driveService = Drive.Builder(NetHttpTransport(), GsonFactory(), requestInitializer)
                    .setApplicationName("Noita Save Uploader")
                    .build()
                // create file metadata
                val fileMetadata = File()
                fileMetadata.name = file.name
                // upload file
                val mediaContent = FileContent("application/zip", file)
                val uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute()
                println("File uploaded: ${uploadedFile.id}")
            } catch (e: Exception) {
                println("Error uploading file: ${e.message}")
            }
        } else {
            println("Invalid file: ${file.absolutePath}")
        }
    }
}
