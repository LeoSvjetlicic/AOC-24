import org.example.common.DATA_BASE_PATH
import org.example.common.loadData

fun day5Puzzle1(): Int {
    val input = mutableListOf<List<Int>>()
    val rules = mutableSetOf<Pair<Int, Int>>()

    var isRulesPart = true
    loadData(DATA_BASE_PATH + "day5/input.txt").forEach {line->
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

    var sum = 0
    input.forEach { update ->
        if (isValid(update)) {
            sum += update[update.size / 2]
        }
    }
    return sum
}
