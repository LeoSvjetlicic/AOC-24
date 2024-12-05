package org.example.day1

import org.example.common.DATA_BASE_PATH
import org.example.common.loadData
import kotlin.math.abs

fun day1Puzzle1(): Int {
    val data = loadData(DATA_BASE_PATH + "day1/input.txt")
    val leftList: MutableList<Int> = mutableListOf()
    val rightList: MutableList<Int> = mutableListOf()
    data.forEach {
        val pair = it.split("   ")
        leftList.add(pair.first().toInt())
        rightList.add(pair.last().toInt())
    }
    leftList.sort()
    rightList.sort()
    return leftList.mapIndexed { index, i -> abs(i - rightList[index]) }.sum()
}
