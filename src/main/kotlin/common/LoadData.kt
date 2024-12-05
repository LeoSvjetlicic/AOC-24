package org.example.common

import java.io.File
import java.io.InputStream

const val DATA_BASE_PATH = "/Users/lsvjetlicic/IdeaProjects/Aoc-24/src/main/kotlin/"

fun loadData(path: String): List<String> {
    val listOfLines = mutableListOf<String>()
    val inputStream: InputStream = File(path).inputStream()
    inputStream.reader().useLines { lines ->
        lines.forEach {
            listOfLines.add(it)
        }
    }
    return listOfLines
}