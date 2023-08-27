package com.android.testapp.util

import java.text.SimpleDateFormat
import java.util.*

fun Date.getDateDifference(expiryDate: Date): Triple<Int, Int, Int> {
    val delta = expiryDate.time - this.time
    if (delta < 0) return Triple(-1, -1, -1)
    val minutes = delta / (60 * 1000)
    val hours = minutes / (60)
    val days = hours / (24)
    return Triple(days.toInt(), (hours % 24).toInt(), (minutes % 60).toInt())
}

fun getDateDifference(expiryTime: Long): Triple<Int, Int, Int> {
    if (expiryTime < 0) return Triple(-1, -1, -1)
    val seconds = expiryTime / 1000
    val minutes = expiryTime / (60 * 1000)
    val hours = minutes / (60)
    val days = hours / (24)
    return Triple((hours % 24).toInt(), (minutes % 60).toInt(), (seconds % 60).toInt())
}

fun getDayName(date: Date): String {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
    val expiry = dateFormatter.parse(dateFormatter.format(date))
    val now = Date()

    if (dateFormatter.format(date) == dateFormatter.format(now)) return "Today"
    return SimpleDateFormat("E", Locale.ENGLISH).format(expiry)
}

fun getDate(date: Date): String {
    val dateFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
    return dateFormatter.format(date.time)
}

fun getTimeSlot(date: Date): String {
//    val timeFormatter = SimpleDateFormat("HH:mm aa")
    val timeFormatter = SimpleDateFormat("h:mm a")
//    timeFormatter.timeZone = TimeZone.getTimeZone(UTC)
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.add(Calendar.MINUTE, 30)
    return "${timeFormatter.format(date)} - ${timeFormatter.format(calendar.time)}"
}

fun getTimeSlot(startDate: Date, endDate: Date): String {
    val timeFormatter = SimpleDateFormat("h:mm a")
//  timeFormatter.timeZone = TimeZone.getTimeZone(UTC)
    return "${timeFormatter.format(startDate)} - ${timeFormatter.format(endDate)}"
}

fun getTimeSlots(startDate: Date, endDate: Date): MutableMap<String, Date> {
    val list = mutableMapOf<String, Date>()
    val timeFormatter = SimpleDateFormat("h:mma")
    val calendar = Calendar.getInstance()
    calendar.time = startDate
    while (calendar.time < endDate) {
        val now = calendar.time
        calendar.add(Calendar.MINUTE, 30)
        list["${timeFormatter.format(now)} - ${timeFormatter.format(calendar.time)}"] = now
    }
    return list
}

fun getTimeSlotAvailability(startDate: Date, endDate: Date): Boolean {
    val timeFormatter = SimpleDateFormat("HH:mm:ss")
    val currentTime = timeFormatter.parse(timeFormatter.format(System.currentTimeMillis()))
    return currentTime.after(startDate) && currentTime.before(endDate)
}

fun isDateTimeInRange(startDate: Date, endDate: Date): Boolean {
    val timeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val currentTime = timeFormatter.parse(timeFormatter.format(System.currentTimeMillis()))
    return currentTime.after(startDate) && currentTime.before(endDate)
}

fun getSimpleDate(date: Date): String {
    return SimpleDateFormat("dd MMM, yyyy").format(date)
}

fun String.to12hClockTime(): String {
    val format24 = SimpleDateFormat("HH:mm:ss")
    val format12 = SimpleDateFormat("hh:mm aa")
    val timeFormat24 = format24.parse(this)
    return format12.format(timeFormat24)
}

fun Date.to12hClockTime(): String {
    val format12 = SimpleDateFormat("hh:mm aa")
    return format12.format(this)
}

fun Date.isDateInFuture(): Boolean {
    val timeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val currentTime = timeFormatter.parse(timeFormatter.format(System.currentTimeMillis()))
    return currentTime <= this
}

fun Date.isDateInPast(): Boolean {
    val timeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val currentTime = timeFormatter.parse(timeFormatter.format(System.currentTimeMillis()))
    return this <= currentTime
}

fun Date.isTimeInFuture(): Boolean {
    val timeFormatter = SimpleDateFormat("HH:mm:ss")
    val currentTime = timeFormatter.parse(timeFormatter.format(System.currentTimeMillis()))
    return currentTime <= this
}

fun Date.isTimeInPast(): Boolean {
    val timeFormatter = SimpleDateFormat("HH:mm:ss")
    val currentTime = timeFormatter.parse(timeFormatter.format(System.currentTimeMillis()))
    return this <= currentTime
}

fun Date?.isNotNull(): Boolean {
    return this != null
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun convertMillisecondsToDate(milliseconds: Long): String {
    val dateFormat = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault())
    val date = Date(milliseconds)
    return dateFormat.format(date)
}