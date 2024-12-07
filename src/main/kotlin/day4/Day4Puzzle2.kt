package org.example.day4

import org.example.common.DATA_BASE_PATH
import org.example.common.loadData

fun day4Puzzle2(): Int {
    val data = loadData(DATA_BASE_PATH + "day4/input.txt")

    val rowCount = data.size
    val colCount = data[0].length
    var count = 0

    fun isXMasAt(r: Int, c: Int): Boolean {
        if (r - 1 < 0 || r + 1 >= rowCount || c - 1 < 0 || c + 1 >= colCount) {
            return false
        }

        val topLeftMAS = data[r - 1][c - 1] == 'M' && data[r][c] == 'A' && data[r + 1][c + 1] == 'S'
        val topLeftSAM = data[r - 1][c - 1] == 'S' && data[r][c] == 'A' && data[r + 1][c + 1] == 'M'

        val topRightMAS = data[r - 1][c + 1] == 'M' && data[r][c] == 'A' && data[r + 1][c - 1] == 'S'
        val topRightSAM = data[r - 1][c + 1] == 'S' && data[r][c] == 'A' && data[r + 1][c - 1] == 'M'

        return (topLeftMAS || topLeftSAM) && (topRightMAS || topRightSAM)
    }

    for (r in 0 until rowCount) {
        for (c in 0 until colCount) {
            if (isXMasAt(r, c)) {
                count++
            }
        }
    }
    return count
}