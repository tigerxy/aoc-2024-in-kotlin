import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int =
        input
            .map { it.split(' ', limit = 2) }
            .map {
                it.first().trim().toInt() to it.last().trim().toInt()
            }
            .let {
                val (left, right) = it.unzip()
                left.sorted().zip(right.sorted())
            }
            .map { (it.first - it.second) }
            .sumOf { it.absoluteValue }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    require(part1(testInput) == 11)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
