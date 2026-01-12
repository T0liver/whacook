package hu.toliver.whacook.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

actual fun getFormattedDateTime(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss")
    return now.format(formatter)
}

