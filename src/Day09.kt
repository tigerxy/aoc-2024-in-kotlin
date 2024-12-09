fun main() {
    fun List<Int>.checksum() =
        filter { 0 <= it }
            .mapIndexed { index, i -> (index * i).toLong() }
            .sum()

    fun part1(input: List<String>): Long =
        input.first()
            .toList()
            .asSequence()
            .map { it.toString().toInt() }
            .chunked(2)
            .flatMapIndexed { index, n ->
                listOf(
                    List(n[0]) { index },
                    List(n.getOrElse(1) { 0 }) { -1 }
                )
            }
            .filter { it.isNotEmpty() }
            .flatten()
            .toList()
            .let { mem ->
                sequence {
                    var indices = mem.indices.toList()
                    while (indices.isNotEmpty()) {
                        var nextIndex = indices.first()
                        indices = indices.drop(1)
                        if (mem[nextIndex] < 0) {
                            indices = indices.reversed()
                            indices = indices.dropWhile { mem[it] < 0 }
                            nextIndex = indices.first()
                            indices = indices.drop(1)
                            indices = indices.reversed()
                        }
                        yield(mem[nextIndex])
                    }
                }
            }
            .toList()
            .checksum()


    fun part2(input: List<String>): Long {
        return input.first()
            .toList()
            .asSequence()
            .map { it.toString().toInt() }
            .chunked(2)
            .flatMapIndexed { index, n ->
                listOf(
                    index to n[0],
                    null to n.getOrElse(1) { 0 }
                )
            }
            .filter { 0 < it.second }
            .toList()
            .let { mem ->
                mem.foldRight(mem) { file, acc ->
                    val oriIndex = acc.indexOfFirst { it.first == file.first }
                    val newIndex = acc.indexOfFirst { it.first == null && file.second <= it.second }
                    if (file.first == null || newIndex == -1 || oriIndex <= newIndex) {
                        acc
                    } else {
                        acc.flatMapIndexed { index, space ->
                            if (index == newIndex) {
                                listOf(file, space.copy(second = space.second - file.second))
                            } else if (space.first == file.first) {
                                listOf(null to file.second)
                            } else {
                                listOf(space)
                            }
                        }.fold(emptyList()) { acc, pair ->
                            if (acc.lastOrNull()?.first == null && pair.first == null) {
                                acc.dropLast(1) + (null to (acc.lastOrNull()?.second ?: 0) + pair.second)
                            } else {
                                acc + pair
                            }
                        }
                    }
                }
            }
            .flatMap { e -> List(e.second) { e.first ?: 0 } }
            .checksum()
    }


// Or read a large test input from the `src/Day01_test.txt` file:
    val day = "09"
    val testInput = readInput("Day${day}_test")
    assertEquals(1928, part1(testInput))
    assertEquals(2858, part2(testInput))

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day$day")
    part1(input).println()
    part2(input).println()
}
