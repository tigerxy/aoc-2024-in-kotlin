data class Pos(val x: Int, val y: Int)

fun main() {
    fun <T> List<T>.allCombinations() =
        flatMapIndexed { index, first ->
            drop(index + 1).map { second ->
                first to second
            }
        }

    fun print(input: List<String>, antiNodes: Set<Pos>) {
        input.mapIndexed { x, row ->
            row.mapIndexed { y, char ->
                if (Pos(x, y) in antiNodes) '#' else char
            }.joinToString("")
        }.forEach { println(it) }
        println()
    }

    fun part1(input: List<String>): Int {
        val antiNodes = input
            .flatMapIndexed { x, s ->
                s.mapIndexed { y, c -> c to Pos(x, y) }
                    .filter { it.first != '.' }
            }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.allCombinations() }
            .values
            .asSequence()
            .flatten()
            .flatMap { (p1, p2) ->
                listOf(
                    Pos(p1.x + (p1.x - p2.x), p1.y + (p1.y - p2.y)),
                    Pos(p2.x - (p1.x - p2.x), p2.y - (p1.y - p2.y))
                )
            }
            .filter { it.x in 0..<input[0].length && it.y in input.indices }
            .toSet()

        print(input, antiNodes)
        return antiNodes.size
    }

    fun part2(input: List<String>): Int {
        val antiNodes = input
            .flatMapIndexed { x, s ->
                s.mapIndexed { y, c -> c to Pos(x, y) }
                    .filter { it.first != '.' }
            }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.allCombinations() }
            .values
            .asSequence()
            .flatten()
            .flatMap { (p1, p2) -> listOf(p1 to p2, p2 to p1) }
            .map { (p1, p2) ->
                generateSequence(p1) { pos ->
                    Pos(pos.x + (p1.x - p2.x), pos.y + (p1.y - p2.y)).takeIf {
                        it.x in 0..<input[0].length && it.y in input.indices
                    }
                }
            }
            .flatMap { it.toList() }
            .toSet()

        print(input, antiNodes)
        return antiNodes.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val day = "08"
    val testInput = readInput("Day${day}_test")
    assertEquals(14, part1(testInput))
    assertEquals(34, part2(testInput))

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day$day")
    part1(input).println()
    part2(input).println()
}
