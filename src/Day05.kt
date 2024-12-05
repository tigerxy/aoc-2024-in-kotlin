fun main() {
    fun part1(input: List<String>): Int {
        val indexOfFirst = input.indexOfFirst { it.isEmpty() }
        val rules =
            input
                .subList(0, indexOfFirst)
                .map { it.split("|").map(String::toInt).let { Pair(it.first(), it.last()) } }
        val updates =
            input
                .subList(indexOfFirst + 1, input.size)
                .map { it.split(",").map(String::toInt) }

        return updates.sumOf { update ->
            rules
                .filter { update.containsAll(it.toList()) }
                .fold(true) { acc, rule ->
                    acc && update.indexOf(rule.first) < update.indexOf(rule.second)
                }
                .let {
                    if (it) update[update.lastIndex / 2]
                    else 0
                }
        }
    }


    fun part2(input: List<String>): Int {
        val indexOfFirst = input.indexOfFirst { it.isEmpty() }
        val rules =
            input
                .take(indexOfFirst)
                .map { it.split("|").map(String::toInt).let { Pair(it.first(), it.last()) } }
        val updates =
            input
                .drop(indexOfFirst + 1)
                .map { it.split(",").map(String::toInt) }

        return updates.sumOf { update ->
            val applicableRules = rules
                .filter { update.containsAll(it.toList()) }

            val sortedUpdate = generateSequence(update) { currentList ->
                val swapped = applicableRules.fold(currentList) { acc, rule ->
                    val left = acc.indexOf(rule.first)
                    val right = acc.indexOf(rule.second)
                    if (left > right) {
                        acc.toMutableList().apply {
                            this[left] = acc[right]
                            this[right] = acc[left]
                        }
                    } else acc
                }
                if (swapped == currentList) null else swapped
            }.last()

            if (update == sortedUpdate) 0
            else sortedUpdate[sortedUpdate.lastIndex / 2]
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val day = "05"
    val testInput = readInput("Day${day}_test")
    require(part1(testInput) == 143)
    require(part2(testInput) == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day$day")
    part1(input).println()
    part2(input).println()
}
