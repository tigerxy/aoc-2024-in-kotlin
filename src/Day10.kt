data class Coord(val x: Int, val y: Int) {
    fun neighbours(): List<Coord> =
        listOf(
            Coord(x - 1, y),
            Coord(x + 1, y),
            Coord(x, y - 1),
            Coord(x, y + 1),
        )
}

operator fun <T> List<List<T>>.contains(pos: Coord): Boolean =
    pos.x in this.indices && pos.y in this.first().indices

operator fun <T> List<List<T>>.get(pos: Coord): T = this[pos.x][pos.y]

fun main() {

    fun part1(input: List<String>): Int {
        val topoMap = input.map { it.toList().map { it.toString().toInt() } }

        val starts = topoMap.flatMapIndexed { x, row ->
            row.mapIndexedNotNull { y, number ->
                Coord(x, y).takeIf { number == 0 }
            }
        }

        return starts
            .map { start ->
                generateSequence(setOf(start)) { positions ->
                    positions.flatMap { pos ->
                        pos.neighbours()
                            .filter { it in topoMap }
                            .filter { topoMap[it] == topoMap[pos] + 1 }
                    }.toSet().takeIf { it.isNotEmpty() }
                }
            }
            .map { it.last() }
            .sumOf { it.size }
    }


    fun part2(input: List<String>): Int {
        val topoMap = input.map { it.toList().map { it.toString().toInt() } }

        val starts = topoMap.flatMapIndexed { x, row ->
            row.mapIndexedNotNull { y, number ->
                Coord(x, y).takeIf { number == 0 }
            }
        }

        return starts
            .map { start ->
                generateSequence(listOf(start)) { positions ->
                    positions.flatMap { pos ->
                        pos.neighbours()
                            .filter { it in topoMap }
                            .filter { topoMap[it] == topoMap[pos] + 1 }
                    }.takeIf { it.isNotEmpty() }
                }
            }
            .map { it.last() }
            .sumOf { it.size }
    }


// Or read a large test input from the `src/Day01_test.txt` file:
    val day = "10"
    val testInput = readInput("Day${day}_test")
    assertEquals(36, part1(testInput))
    assertEquals(81, part2(testInput))

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day$day")
    part1(input).println()
    part2(input).println()
}
