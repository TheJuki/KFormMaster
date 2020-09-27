package com.thejuki.kformmaster.extensions

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.util.*

fun LocalDate.toDate(): Date? {
    val cal = Calendar.getInstance()
    cal.set(this.year, this.monthValue - 1, this.dayOfMonth)

    return cal.time
}

fun LocalDateTime.toDate(): Date? {
    val cal = Calendar.getInstance()
    cal.set(this.year, this.monthValue - 1, this.dayOfMonth, this.hour, this.minute)

    return cal.time
}

fun Date.toLocalDate(): LocalDate {
    val cal = Calendar.getInstance()
    cal.time = this

    return LocalDate.of(cal[Calendar.YEAR], cal[Calendar.MONTH] + 1, cal[Calendar.DAY_OF_MONTH])
}

fun Date.toLocalDateTime(): LocalDateTime {
    val cal = Calendar.getInstance()
    cal.time = this

    return LocalDateTime.of(cal[Calendar.YEAR], cal[Calendar.MONTH] + 1, cal[Calendar.DAY_OF_MONTH], cal[Calendar.HOUR_OF_DAY], cal[Calendar.MINUTE])
}

operator fun LocalDate.rangeTo(other: LocalDate) = DateProgression(this, other)