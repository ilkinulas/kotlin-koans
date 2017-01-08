package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override fun compareTo(other: MyDate): Int {
        return (this.year*365 + this.month*30 + this.dayOfMonth) -
                (other.year*365 + other.month*30 + other.dayOfMonth)
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class RepeatedTimeInterval(val ti:TimeInterval, val n:Int)

operator fun TimeInterval.times(n:Int) = RepeatedTimeInterval(this, n)

operator fun MyDate.plus(interval: TimeInterval)
        = addTimeIntervals(interval, 1)

operator fun MyDate.plus(repeatedTimeInterval : RepeatedTimeInterval)
        = addTimeIntervals(repeatedTimeInterval.ti, repeatedTimeInterval.n)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {

        return object : Iterator<MyDate> {

            var current : MyDate = start
            override fun hasNext(): Boolean {
                return current <= endInclusive
            }

            override fun next(): MyDate {
                val result = current
                current = current.nextDay()
                return result
            }
        }
    }
}

operator fun DateRange.contains(date: MyDate): Boolean {
    return date > start && date < endInclusive
}
