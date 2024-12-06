package org.example.day3

import org.example.common.DATA_BASE_PATH
import org.example.common.loadData

fun day3Puzzle1(): Int {
    val data = loadData(DATA_BASE_PATH + "day3/input.txt")[0]
    val regexMul = Regex("""mul\(\d+,\d+\)""")
    val matches = regexMul.findAll(data)

    return matches.map { match ->
        val numbersRegex = Regex("""\d+""")
        val numbers = numbersRegex.findAll(match.value).map { it.value.toInt() }.toList()
        numbers[0] * numbers[1]
    }.sum()
}