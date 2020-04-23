package com.entimer.coronatracker.util

import android.content.Context
import java.io.*
import java.lang.Exception

class FileUtil(context: Context) {
    companion object {
        const val GLOBAL = "global.json"
    }

    private val context = context

    fun write(fileName: String, fileContent: String) {
        try {
            val outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE) as FileOutputStream
            outStream.write(fileContent.toByteArray(Charsets.UTF_8))
            outStream.close()
    }
        catch(e: Exception) {
            e.printStackTrace()
        }
    }

    fun read(fileName: String): String {
        var fileContent = ""
        try {
            val inStream = context.openFileInput(fileName)
            val stringBuffer = StringBuffer()
            val dataBuffer: ByteArray = ByteArray(inStream.available())
            var isEof = false

            while(!isEof) {
                val data = inStream.read(dataBuffer)
                if(data == -1) {
                    isEof = true
                    continue
                }
                stringBuffer.append(String(dataBuffer))
            }
            inStream.close()

            return stringBuffer.toString()
        }
        catch(e: Exception) {
            e.printStackTrace()
        }

        return fileContent
    }
}