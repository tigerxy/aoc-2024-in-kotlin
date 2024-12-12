import kotlin.math.log10

fun main() {
    fun part1(input: List<String>): Int {
        var stones = input.first().split(" ").map { it.toLong() }
        repeat(25) {
            stones = stones.flatMap { stone ->
                val stoneStr = stone.toString()
                if (stone == 0L) {
                    listOf(1)
                } else if (stoneStr.length % 2 == 0) {
                    stoneStr.chunked(stoneStr.length / 2) {
                        it.toString().toLong()
                    }
                } else {
                    listOf(stone * 2024)
                }
            }
        }
        return stones.size
    }

    class Stone(private val number: Long) {
        private val cache = mutableMapOf<Int, Long>()

        fun getSize(timesLeft: Int, otherStones: (number: Long) -> Stone): Long {
            if (timesLeft == 0) return 1L
            return cache.getOrElse(timesLeft) {
                if (number == 0L) {
                    otherStones(1).getSize(timesLeft - 1, otherStones)
                } else {
                    val length = (log10(number.toDouble()) + 1).toInt()
                    if (length % 2 == 0) {
                        number.toString().chunked(length / 2).sumOf {
                            otherStones(it.toLong()).getSize(timesLeft - 1, otherStones)
                        }
                    } else {
                        otherStones(number * 2024).getSize(timesLeft - 1, otherStones)
                    }.also {
                        cache[timesLeft] = it
                    }
                }
            }
        }
    }

    fun part2(input: List<String>, times: Int): Long {
        val stoneNumbers = input.first().split(" ").map { it.toLong() }
        val stoneCache = mutableMapOf<Long, Stone>()

        return stoneNumbers.sumOf { stoneNumber ->
            stoneCache.getOrPut(stoneNumber) {
                Stone(stoneNumber)
            }.getSize(times) {
                stoneCache.getOrPut(it) {
                    Stone(it)
                }
            }
        }
    }


// Or read a large test input from the `src/Day01_test.txt` file:
    val day = "11"
    val testInput = readInput("Day${day}_test")
    assertEquals(55312, part1(testInput))
    assertEquals(55312, part2(testInput, 25))

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day$day")
    part1(input).println()
    part2(input, 75).println()
}
