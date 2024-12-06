package org.example.day2

import org.example.common.DATA_BASE_PATH
import org.example.common.loadData
import kotlin.math.abs

fun day2Puzzle2(): Int {
    val data = loadData(DATA_BASE_PATH + "day2/input.txt")
    return data.map {
        val number = it.split(" ").map { number -> number.toInt() }
        if (isReportValid2(number)) {
            1
        } else {
            var isAnyTrue = false
            for (index in number.indices) {
                val tempNumber = number.toMutableList()
                tempNumber.removeAt(index)
                isAnyTrue = if (isReportValid2(tempNumber)) true else isAnyTrue
            }
            if (isAnyTrue) 1 else 0
        }
    }.sum()
}

fun isReportValid2(levels: List<Int>): Boolean {
    val ascendingLevels = levels.sorted()
    val descendingLevels = levels.sortedDescending()
    if (ascendingLevels == levels || descendingLevels == levels) {
        for (index in 1..<levels.size - 1) {
            val differencePrevious = abs(levels[index] - levels[index - 1])
            val differenceNext = abs(levels[index] - levels[index + 1])
            if (differencePrevious !in 1..3 || differenceNext !in 1..3) {
                return false
            }
        }
    } else {
        return false
    }
    return true
}
