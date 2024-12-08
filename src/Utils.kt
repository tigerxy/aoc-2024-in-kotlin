import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun <T> List<List<T>>.rotate(): List<List<T>> {
    return List(size) { col ->
        List(size) { row ->
            this[size - 1 - row][col]
        }
    }
}

fun <T> List<List<T>>.transpose(): List<List<T>> {
    return List(this[0].size) { col ->
        List(size) { row ->
            this[row][col]
        }
    }
}

fun <T> List<List<T>>.mainDiagonal(): List<T> =
    (indices).map { i -> this[i][i] }

fun <T> List<List<T>>.antiDiagonal(): List<T> =
    (indices).map { i -> this[i][this.size - 1 - i] }

fun <T> List<List<T>>.mainTypeDiagonals(): List<List<T>> {
    val n = size
    val m = this[0].size
    return (-m + 1 until n).map { offset ->
        (0 until n).mapNotNull { row ->
            val col = row - offset
            if (col in 0 until m) this[row][col] else null
        }
    }
}

fun <T> List<List<T>>.antiTypeDiagonals(): List<List<T>> {
    val n = size
    val m = this[0].size
    return (0 until n + m - 1).map { sum ->
        (0 until n).mapNotNull { row ->
            val col = sum - row
            if (col in 0 until m) this[row][col] else null
        }
    }
}

fun List<Char>.asString() = joinToString("")

inline fun <reified T> Array<Array<T>>.deepCopy() = Array(size) { i -> get(i).clone() }

fun <T> assertEquals(expected: T, actual: T, message: String? = null) {
    if (expected != actual) {
        throw AssertionError(message ?: "Assertion failed: Expected <$expected>, but got <$actual>")
    }
}
