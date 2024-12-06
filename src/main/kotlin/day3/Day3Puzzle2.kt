package org.example.day3

import org.example.common.DATA_BASE_PATH
import org.example.common.loadData

fun day3Puzzle2(): Int {
    var shouldCalculate = true
    var data = loadData(DATA_BASE_PATH + "day3/input.txt")[0]
    val regexMul = Regex("""mul\(\d+,\d+\)""")
    val regexDo = Regex("""do\(\)""")
    val regexDont = Regex("""don\'t\(\)""")
    var finalSum = 0

    while (true) {
        val mulValue = regexMul.find(data)
        val doValue = regexDo.find(data)
        val dontValue = regexDont.find(data)

        val mulIndex = mulValue?.range?.first ?: -1
        val doIndex = doValue?.range?.first ?: -1
        val dontIndex = dontValue?.range?.first ?: -1

        val validIndices = listOf(mulIndex, doIndex, dontIndex).filter { it >= 0 }
        if (validIndices.isEmpty()) break

        val smallestIndex = validIndices.minOrNull() ?: break

        when (smallestIndex) {
            mulIndex -> {
                if (shouldCalculate) {
                    val numbers = Regex("""\d+""")
                        .findAll(mulValue!!.value)
                        .map { it.value.toInt() }
                        .toList()
                    finalSum += numbers[0] * numbers[1]
                }
                data = data.replaceFirst(mulValue!!.value, "")
            }
            doIndex -> {
                shouldCalculate = true
                data = data.replaceFirst(doValue!!.value, "")
            }
            dontIndex -> {
                shouldCalculate = false
                data = data.replaceFirst(dontValue!!.value, "")
            }
        }
    }

    return finalSum
}