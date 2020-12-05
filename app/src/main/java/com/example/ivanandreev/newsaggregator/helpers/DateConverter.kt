package com.example.ivanandreev.newsaggregator.helpers

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class DateConverter {
    companion object {
        fun fromIsoString(isoDate: String): Calendar {
            return GregorianCalendar.from(
                ZonedDateTime.ofInstant(
                    Instant.parse(isoDate),
                    ZoneId.systemDefault()
                )
            )
        }

        fun toIsoString(calendar: Calendar): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.UK)
            return sdf.format(calendar.time)
        }

        fun toDayMonthYearString(calendar: Calendar): String {
            val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.UK)
            return sdf.format(calendar.time)
        }
    }
}