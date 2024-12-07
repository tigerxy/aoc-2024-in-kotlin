fun main() {
    fun List<Int>.isSafe(): Boolean =
        zipWithNext { a, b -> b - a }
            .let { differences ->
                differences.all { it in 1..3 } || differences.all { it in -3..-1 }
            }

    fun List<Int>.isSafeWithDampener(): Boolean {
        if (isSafe()) return true
        return indices
            .map { toMutableList().apply { removeAt(it) } }
            .any { it.isSafe() }
    }

    fun part1(input: List<String>): Int =
        input
            .map {
                it
                    .split(' ')
                    .map { it.trim().toInt() }
                    .isSafe()
            }
            .count { it }

    fun part2(input: List<String>): Int =
        input
            .map {
                it
                    .split(' ')
                    .map { it.trim().toInt() }
            }
            .map { it.isSafeWithDampener() }
            .count { it }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val day = "02"
    val testInput = readInput("Day${day}_test")
    require(part1(testInput) == 2)
    require(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day$day")
    part1(input).println()
    part2(input).println()
}
