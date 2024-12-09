package org.example.day6

import org.example.common.DATA_BASE_PATH
import org.example.common.loadData
import org.example.day6.common.GuardPosition

fun day6Puzzle1(): Int {
    val data = loadData(DATA_BASE_PATH + "day6/input.txt").map { it.split("").toMutableList() }.toMutableList()
    val guardPlaceX = data.find { it.any { char -> char == "v" || char == "^" || char == "<" || char == ">" } }!!
    val guardPlaceY = guardPlaceX.find { it == "v" || it == ">" || it == "^" || it == "<" }!!
    val indexXGuard = data.indexOf(guardPlaceX)
    val indexYGuard = guardPlaceX.indexOf(guardPlaceY)
    var isRunning = true
    var startingGuard = GuardPosition(
        direction = getDirection(data[indexXGuard][indexYGuard]),
        indexX = indexXGuard,
        indexY = indexYGuard
    )
    while (isRunning) {
        move(
            data = data,
            position = startingGuard,
            onPositionChange = { newValue, oldValue, didMove ->
                startingGuard = newValue
                if (didMove) {
                    data[oldValue.indexX][oldValue.indexY] = "X"
                    data[newValue.indexX][newValue.indexY] = getDirection(newValue.direction)
                }
            },
            isOver = { isRunning = false }
        )
    }
    var sum = 1
    data.forEach { row ->
        sum += row.count { it == "X" }
    }
    return sum
}

private fun getDirection(directionTag: String) = when (directionTag) {
    "^" -> 0
    ">" -> 1
    "v" -> 2
    else -> 3
}

private fun getDirection(directionTag: Int) = when (directionTag) {
    0 -> "^"
    1 -> ">"
    2 -> "v"
    else -> "<"
}

private fun move(
    data: List<List<String>>,
    position: GuardPosition,
    onPositionChange: (GuardPosition, GuardPosition, Boolean) -> Unit,
    isOver: () -> Unit
): GuardPosition {
    val (direction, indexX, indexY) = position

    val newPosition = when (direction) {

        0 -> if (indexX - 1 >= 0) {
            position.copy(indexX = indexX - 1)
        } else null

        1 -> if (indexY + 1 < data[indexX].size) {
            position.copy(indexY = indexY + 1)
        } else null

        2 -> if (indexX + 1 < data.size) {
            position.copy(indexX = indexX + 1)
        } else null

        else -> if (indexY - 1 >= 0) {
            position.copy(indexY = indexY - 1)
        } else null
    }

    return if (newPosition != null) {
        if (data[newPosition.indexX][newPosition.indexY] == "#") {
            onPositionChange(position.copy(direction = (direction + 1) % 4), position, false)
            position.copy(direction = (direction + 1) % 4)
        } else {
            onPositionChange(newPosition, position, true)
            newPosition
        }
    } else {
        isOver()
        position
    }
}
