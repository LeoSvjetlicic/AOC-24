package org.example.day5

import org.example.common.DATA_BASE_PATH
import org.example.common.loadData

fun day5Puzzle2(): Int {
    val input = mutableListOf<List<Int>>()
    val rules = mutableSetOf<Pair<Int, Int>>()

    var isRulesPart = true
    loadData(DATA_BASE_PATH + "day5/input.txt").forEach { line ->
        if (line.isEmpty()) {
            isRulesPart = false
        } else if (isRulesPart) {
            val (x, y) = line.split("|").map { it.toInt() }
            rules.add(x to y)
        } else {
            input.add(line.split(",").map { it.toInt() })
        }
    }

    fun isValid(update: List<Int>): Boolean {
        for ((x, y) in rules) {
            val xIndex = update.indexOf(x)
            val yIndex = update.indexOf(y)
            if (xIndex != -1 && yIndex != -1 && xIndex >= yIndex) return false
        }
        return true
    }

    fun fixOrder(update: List<Int>): List<Int> {
        val graph = mutableMapOf<Int, MutableList<Int>>()
        val inDegree = mutableMapOf<Int, Int>().withDefault { 0 }

        update.forEach { page ->
            graph.putIfAbsent(page, mutableListOf())
            inDegree[page] = inDegree.getValue(page)
        }
        for ((x, y) in rules) {
            if (x in update && y in update) {
                graph[x]?.add(y)
                inDegree[y] = inDegree.getValue(y) + 1
            }
        }

        val queue = ArrayDeque<Int>()
        inDegree.forEach { (node, degree) -> if (degree == 0) queue.add(node) }
        val sorted = mutableListOf<Int>()
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            sorted.add(current)
            for (neighbor in graph[current] ?: emptyList()) {
                inDegree[neighbor] = inDegree.getValue(neighbor) - 1
                if (inDegree.getValue(neighbor) == 0) queue.add(neighbor)
            }
        }
        return sorted
    }

    var sum = 0
    input.forEach { update ->
        if (!isValid(update)) {
            val fixedUpdate = fixOrder(update)
            sum += fixedUpdate[fixedUpdate.size / 2]
        }
    }
    return sum
}
