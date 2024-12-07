package org.example.day4

import org.example.common.DATA_BASE_PATH
import org.example.common.loadData

fun day4Puzzle1(): Int {
    val data = loadData(DATA_BASE_PATH + "day4/input.txt")
    val word = "XMAS"
    val rowCount = data.size
    val colCount = data[0].length
    var count = 0

    val directions = listOf(
        Pair(0, 1),
        Pair(0, -1),
        Pair(1, 0),
        Pair(-1, 0),
        Pair(1, 1),
        Pair(-1, -1),
        Pair(1, -1),
        Pair(-1, 1)
    )

    fun isWordAt(r: Int, c: Int, dr: Int, dc: Int): Boolean {
        for (i in word.indices) {
            val nr = r + i * dr
            val nc = c + i * dc
            if (nr !in 0 until rowCount || nc !in 0 until colCount || data[nr][nc] != word[i]) {
                return false
            }
        }
        return true
    }

    for (r in 0 until rowCount) {
        for (c in 0 until colCount) {
            for ((dr, dc) in directions) {
                if (isWordAt(r, c, dr, dc)) {
                    count++
                }
            }
        }
    }

    return count
}

