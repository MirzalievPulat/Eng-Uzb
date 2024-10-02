package uz.gita.eng_uzb.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun String.createSpannable(query: String): SpannableString {
    val spannable = SpannableString(this)
    val startIndex = this.indexOf(query)
    val endIndex = startIndex + query.length
    if (startIndex < 0 || endIndex > this.length) return spannable
    spannable.setSpan(
        ForegroundColorSpan(Color.parseColor("#03A9F4")),
        startIndex, // start
        endIndex, // end
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
    return spannable
}


fun Long.toPattern():String{
    return SimpleDateFormat("dd/MM/yy",Locale.getDefault()).format(Date(this))
}
