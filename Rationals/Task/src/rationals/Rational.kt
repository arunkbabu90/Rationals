package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}

infix fun BigInteger.divBy(denominator: BigInteger) = Rational(this, denominator)
infix fun Int.divBy(denominator: Int) = Rational(this.toBigInteger(), denominator.toBigInteger())
infix fun Long.divBy(denominator: Long) = Rational(this.toBigInteger(), denominator.toBigInteger())

fun String.toRational(): Rational {
    val numerator: BigInteger
    var denominator: BigInteger = BigInteger.ONE
    // Split the string into numerator and denominator
    val numeratorDenominatorList = split('/', limit = 2)

    if (numeratorDenominatorList.size == 1) {
        // Only Numerator exists
        numerator = numeratorDenominatorList[0].toBigInteger()
    } else {
        // Both Numerator and Denominator exists
        numerator = numeratorDenominatorList[0].toBigInteger()
        denominator = numeratorDenominatorList[1].toBigInteger()
    }

    return Rational(numerator, denominator)
}

class Rational(private val numerator: BigInteger, private val denominator: BigInteger): Comparable<Rational> {
    init {
        if (denominator == BigInteger.ZERO) throw IllegalArgumentException()
    }

    operator fun plus(rational: Rational): Rational {
        val a = numerator * rational.denominator
        val b = rational.numerator * denominator
        return Rational(a + b, denominator * rational.denominator)
    }

    operator fun minus(rational: Rational): Rational {
        val a = numerator * rational.denominator
        val b = rational.numerator * denominator
        return Rational(a - b, denominator * rational.denominator)
    }

    operator fun unaryMinus() = Rational(-numerator, denominator)
    operator fun times(rational: Rational) = numerator * rational.numerator divBy denominator * rational.denominator
    operator fun div(rational: Rational) = numerator * rational.denominator divBy denominator * rational.numerator
    override fun compareTo(other: Rational) = (numerator * other.denominator).compareTo(denominator * other.numerator)
    override fun equals(other: Any?) = if (other is Rational) numerator * other.denominator == denominator * other.numerator else false

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }

    override fun toString(): String {
        // Normalize the rationals
        val gcd = numerator.gcd(denominator)
        val normalizedRational = if (denominator < BigInteger.ZERO)
            Rational(-(numerator / gcd), -(denominator / gcd))
        else
            Rational(numerator / gcd, denominator / gcd)

        // Omit the denominator if its 1
        return if (normalizedRational.denominator == BigInteger.ONE) "${normalizedRational.numerator}" else "${normalizedRational.numerator}/${normalizedRational.denominator}"
    }
}