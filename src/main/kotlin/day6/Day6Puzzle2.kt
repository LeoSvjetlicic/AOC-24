package org.example.day6

import org.example.common.DATA_BASE_PATH
import org.example.common.loadData
import org.example.day6.common.GuardPosition

fun day6Puzzle2(): Int {
    // Load the map data
    val originalData = loadData(DATA_BASE_PATH + "day6/input.txt").map { it.split("").toMutableList() }.toMutableList()

    // Find the guard's starting position
    val guardRow = originalData.find { it.any { char -> char in setOf("v", "^", "<", ">") } }!!
    val guardChar = guardRow.find { it in setOf("v", "^", "<", ">") }!!
    val startX = originalData.indexOf(guardRow)
    val startY = guardRow.indexOf(guardChar)

    // Get guard's starting direction
    val guardDirection = getDirection(guardChar)

    // List of positions that cause loops
    val loopablePositions = mutableSetOf<Pair<Int, Int>>()

    // Iterate over all possible positions
    for (x in originalData.indices) {
        for (y in originalData[x].indices) {
            // Skip if the position is not empty or is the starting position
            if (originalData[x][y] != "." || (x == startX && y == startY)) continue

            // Create a copy of the map and add the obstruction
            val testData = originalData.map { it.toMutableList() }
            testData[x][y] = "#"

            // Simulate the guard's movement
            val isLoop = doesCauseLoop(testData, GuardPosition(guardDirection, startX, startY))

            // If it causes a loop, add the position to the set
            if (isLoop) {
                loopablePositions.add(Pair(x, y))
            }
        }
    }

    // Return the number of positions that cause loops
    return loopablePositions.size
}

// Function to check if adding an obstruction causes a loop
private fun doesCauseLoop(data: List<List<String>>, start: GuardPosition): Boolean {
    val visitedStates = mutableSetOf<Triple<Int, Int, Int>>()
    var guard = start
    var isOver = false
    while (!isOver) {
        val currentState = Triple(guard.indexX, guard.indexY, guard.direction)

        // If the current state has been visited, a loop is detected
        if (currentState in visitedStates) return true
        visitedStates.add(currentState)

        // Move the guard
        guard = move(
            data = data,
            position = guard,
            onPositionChange = { newGuard, _ -> guard = newGuard },
            isOver = { isOver = true } // Exit if the guard leaves the map
        )
    }
    return false
}

// Helper functions (move and getDirection) remain the same as in Part 1

private fun getDirection(directionTag: String): Int = when (directionTag) {
    "^" -> 0
    ">" -> 1
    "v" -> 2
    else -> 3
}

private fun move(
    data: List<List<String>>,
    position: GuardPosition,
    onPositionChange: (GuardPosition, Boolean) -> Unit,
    isOver: () -> Unit
): GuardPosition {
    val (direction, indexX, indexY) = position

    val newPosition = when (direction) {
        0 -> if (indexX - 1 >= 0) position.copy(indexX = indexX - 1) else null // Move up
        1 -> if (indexY + 1 < data[indexX].size) position.copy(indexY = indexY + 1) else null // Move right
        2 -> if (indexX + 1 < data.size) position.copy(indexX = indexX + 1) else null // Move down
        else -> if (indexY - 1 >= 0) position.copy(indexY = indexY - 1) else null // Move left
    }

    return if (newPosition != null) {
        if (data[newPosition.indexX][newPosition.indexY] == "#") {
            // Turn right if there's an obstacle
            onPositionChange(position.copy(direction = (direction + 1) % 4), false)
            position.copy(direction = (direction + 1) % 4)
        } else {
            // Move to the new position
            onPositionChange(newPosition, true)
            newPosition
        }
    } else {
        // Guard moves out of bounds, end the simulation
        isOver()
        position
    }
}
