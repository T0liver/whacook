package hu.toliver.whacook.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun getFormattedDateTime(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.getDefault())
    return formatter.format(Date())
}

