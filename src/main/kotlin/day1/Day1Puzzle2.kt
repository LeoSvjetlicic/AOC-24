package org.example.day1

import org.example.common.DATA_BASE_PATH
import org.example.common.loadData

fun day1Puzzle2(): Int {
    val data = loadData(DATA_BASE_PATH + "day1/input.txt")
    val right: MutableMap<String, Int> = mutableMapOf()
    val initialLeftList = data.map {
        val pair = it.split("   ")
        right[pair.last()] = (right[pair.last()] ?: 0) + 1
        pair.first()
    }
    return initialLeftList.sumOf {
        it.toInt() * (right[it] ?: 0)
    }
}
