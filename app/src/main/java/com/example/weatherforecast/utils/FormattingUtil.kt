package com.example.weatherforecast.utils

import java.text.SimpleDateFormat
import java.util.Locale

object FormattingUtil {
    private const val DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"
    private const val DATE_FORMAT_EEE_D_MMM = "EEE, d MMM"


    fun getFormatDate(date: Long?): String? {
        var formattedDate: String? = null
        if (date != null) {
            val dateFormat = SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD, Locale.getDefault())
            formattedDate = dateFormat.format(date * 1000)
        }
        return formattedDate
    }

    fun getDateFormatEEE(date: Long?): String? {
        var formattedDate: String? = null
        if (date != null) {
            val dateFormat = SimpleDateFormat(DATE_FORMAT_EEE_D_MMM, Locale.getDefault())
            formattedDate = dateFormat.format(date * 1000)
        }
        return formattedDate
    }


}