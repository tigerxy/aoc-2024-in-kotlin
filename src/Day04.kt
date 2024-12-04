fun main() {
    val word = "XMAS"
    fun part1(input: List<String>): Int =
        (input.map { it.toList() })
            .let { matrix ->
                matrix + matrix.rotate() + matrix.mainTypeDiagonals() + matrix.antiTypeDiagonals()
            }
            .flatMap { it.windowed(4) }
            .map { it.asString() }
            .count { it == word || it == word.reversed() }

    fun part2(input: List<String>): Int =
        (input.map { it.toList() })
            .windowed(3)
            .flatMap { it.transpose().windowed(3) }
            .count {
                val mainDiagonalString = it.mainDiagonal().asString()
                val antiDiagonalString = it.antiDiagonal().asString()
                val mas = word.takeLast(3)
                (mainDiagonalString == mas || mainDiagonalString == mas.reversed())
                        && (antiDiagonalString == mas || antiDiagonalString == mas.reversed())
            }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val day = "04"
    val testInput = readInput("Day${day}_test")
    require(part1(testInput) == 18)
    require(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day$day")
    part1(input).println()
    part2(input).println()
}
