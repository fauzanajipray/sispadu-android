package com.devajip.sispadu.common

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.devajip.sispadu.R
import com.devajip.sispadu.presentation.theme.Blue700
import com.devajip.sispadu.presentation.theme.Orange300
import com.devajip.sispadu.presentation.theme.Red700
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

lateinit var statusComplaint: StatusComplaint
const val TAG = "Utils"

@Composable
fun getStatusComplaint(status: String, toName: String = ""): StatusComplaint {
    when (status) {
        Constant.STATUS_PENDING -> {
            statusComplaint = StatusComplaint(
                Constant.STATUS_PENDING,
                stringResource(R.string.msg_when_complaint_pending),
                R.drawable.ic_pending,
                Orange300
            )
        }
        Constant.STATUS_FORWARD -> {
            statusComplaint = StatusComplaint(
                Constant.STATUS_FORWARD,
                stringResource(R.string.msg_when_complaint_forwarded, toName),
                R.drawable.ic_forward,
                Blue700
            )
        }
        Constant.STATUS_ACCEPTED -> {
            statusComplaint = StatusComplaint(
                Constant.STATUS_ACCEPTED,
                stringResource(R.string.msg_when_complaint_approved, toName),
                R.drawable.ic_done,
                Orange300
            )
        }
        Constant.STATUS_REJECTED -> {
            statusComplaint = StatusComplaint(
                Constant.STATUS_REJECTED,
                stringResource(R.string.msg_when_complaint_rejected, toName),
                R.drawable.ic_rejected,
                Red700
            )
        }
    }
    return statusComplaint
}

@SuppressLint("SimpleDateFormat")
@Composable
fun getDateForHuman(date: String): String {

    val formatterSimple: DateFormat = SimpleDateFormat(stringResource(R.string.date_format_patern_1))
    val formatterMonth: DateFormat = SimpleDateFormat(stringResource(R.string.date_format_patern_2))
    val past: Date? = formatterSimple.parse(date)
    val now: Date? = formatterSimple.parse(formatterSimple.format(Date()))
    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now!!.time - past!!.time)
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
    val hours: Long = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
    val days: Long = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

    Log.d(TAG, "getDateForHuman: $date")
    Log.d(TAG, "past: $past")
    Log.d(TAG, "now: $now")
    Log.d(TAG, "seconds: $seconds")
    Log.d(TAG, "min utes: $minutes")
    Log.d(TAG, "hours: $hours")
    Log.d(TAG, "days: $days")

    return when {
        seconds < 60 -> "$seconds seconds ago"
        minutes < 60 -> "$minutes minutes ago"
        hours < 24 -> "$hours hours ago"
        days < 7 -> "$days days ago"
        else -> formatterMonth.format(past)
    }
}