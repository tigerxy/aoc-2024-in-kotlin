fun main() {
    fun part1(input: List<String>): Long =
        input
            .map { row ->
                "\\d+".toRegex().findAll(row).map { it.value.toLong() }
            }
            .mapNotNull { row ->
                row.first().takeIf { c ->
                    row.drop(1).toList()
                        .map { listOf(it) }.reduce { acc, n ->
                            acc.flatMap {
                                listOf(
                                    it + n.first(),
                                    it * n.first()
                                )
                            }
                        }
                        .any { it == c }
                }
            }
            .sum()

    fun part2(input: List<String>): Long =
        input
            .map { row ->
                "\\d+".toRegex().findAll(row).map { it.value.toLong() }
            }
            .mapNotNull { row ->
                row.first().takeIf { c ->
                    row.drop(1).toList()
                        .map { listOf(it) }.reduce { acc, n ->
                            acc.flatMap {
                                listOf(
                                    it + n.first(),
                                    it * n.first(),
                                    (it.toString() + n.first().toString()).toLong()
                                )
                            }
                        }
                        .any { it == c }
                }
            }
            .sum()

    // Or read a large test input from the `src/Day01_test.txt` file:
    val day = "07"
    val testInput = readInput("Day${day}_test")
    require(part1(testInput) == 3749L)
    require(part2(testInput) == 11387L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day$day")
    part1(input).println()
    part2(input).println()
}
