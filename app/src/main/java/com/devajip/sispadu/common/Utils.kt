package com.devajip.sispadu.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.devajip.sispadu.R
import com.devajip.sispadu.presentation.theme.Blue700
import com.devajip.sispadu.presentation.theme.Orange300
import com.devajip.sispadu.presentation.theme.Red700

lateinit var statusComplaint: StatusComplaint

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