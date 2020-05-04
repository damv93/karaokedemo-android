package com.example.karaokedemo.presentation.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel
import java.text.SimpleDateFormat
import java.util.*

object FileUtil {

    fun createFileUri(context: Context) : Uri? {
        return try {
            val file = createFile(context)
            FileProvider.getUriForFile(
                context,
                "com.example.karaokedemo.fileprovider",
                file
            )
        } catch (e: IOException) {
            null
        }
    }

    fun copyToNewFile(context: Context, sourcePath: String): Uri? {
        var source: FileChannel? = null
        var destination: FileChannel? = null
        try {
            val newFile = createFile(context)
            source = FileInputStream(sourcePath).channel
            destination = FileOutputStream(newFile).channel
            destination.transferFrom(source, 0, source.size())
            return FileProvider.getUriForFile(
                context,
                "com.example.karaokedemo.fileprovider",
                newFile
            )
        } catch (e: IOException){
            return null
        } finally {
            source?.close()
            destination?.close()
        }
    }

    @Throws(IOException::class)
    private fun createFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

}