package com.entimer.coronatracker.util

import android.content.Context
import java.io.*
import java.lang.Exception

class CacheUtil(context: Context) {
    companion object {
        const val GLOBAL = "global.json"
    }

    private val context = context
    private val dir = context.cacheDir

    fun write(fileName: String, fileContent: String) {
        try {
            val writer = BufferedWriter(FileWriter(dir.toString() + fileName, false))
            writer.write(fileContent)
            writer.close()
        }
        catch(e: Exception) {
            e.printStackTrace()
        }
    }

    fun read(fileName: String): String {
        var fileContent = ""

        try {
            val reader = BufferedReader(FileReader(dir.toString() + fileName))
            while(reader.readLine() != null) {
                fileContent += reader.readLine()
            }
            reader.close()
        }
        catch(e: Exception) {
            e.printStackTrace()
        }

        return fileContent
    }
}