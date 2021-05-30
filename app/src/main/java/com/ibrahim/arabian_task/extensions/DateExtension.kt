package com.ibrahim.arabian_task.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.timeStampToFormattedString(): String{
    val newDate: Date = Date(this.times(1000))
    val format = SimpleDateFormat("EEEE, dd MMM yyyy")
    return format.format(newDate)
}