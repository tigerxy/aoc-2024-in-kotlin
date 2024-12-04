fun main() {
    fun part1(input: List<String>): Int =
        "mul\\((\\d{1,3}),(\\d{1,3})\\)"
            .toRegex()
            .findAll(input.joinToString(""))
            .sumOf {
                it.groupValues
                    .drop(1)
                    .map(String::toInt)
                    .let { (a, b) -> a * b }
            }

    fun part2(input: List<String>): Int =
        "mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)"
            .toRegex()
            .findAll(input.joinToString(""))
            .fold(0 to true) { acc, matchResult ->
                when {
                    matchResult.value == "do()" -> acc.copy(second = true)
                    matchResult.value == "don't()" -> acc.copy(second = false)
                    acc.second -> acc.copy(
                        first = acc.first + matchResult
                            .groupValues
                            .takeLast(2)
                            .map(String::toInt)
                            .let { (a, b) -> a * b }
                    )

                    else -> acc
                }
            }.first

    // Or read a large test input from the `src/Day01_test.txt` file:
    val day = "03"
    val testInput = readInput("Day${day}_test")
    require(part1(testInput) == 161)
    require(part2(testInput) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day$day")
    // 28546082 too low
    part1(input).println()
    part2(input).println()
}
