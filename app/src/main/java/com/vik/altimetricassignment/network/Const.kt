package com.vik.altimetricassignment.network

import com.vik.altimetricassignment.model.Result
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object Const {

    object URLS {

        const val BASE_URL = "https://itunes.apple.com/"
        const val SEARCH = "search"

    }

    fun searchCustomersFilter(results: List<Result>?, charString: String): List<Result>? {
        val filteredTempList: MutableList<Result> =
            java.util.ArrayList<Result>()
        for (collection in results!!) {
            if (collection != null) {
                if (containsIgnoreCase(collection.collectionCensoredName, charString)) {
                    filteredTempList.add(collection)
                }
            }
        }
        return filteredTempList
    }

    private fun containsIgnoreCase(source: String?, wantedStr: String): Boolean {
        val length = wantedStr.length
        return if (length == 0) {
            true
        } else Pattern.compile(
            Pattern.quote(wantedStr),
            Pattern.CASE_INSENSITIVE
        ).matcher(source).find()
    }

    fun getDate(ourDate: String): String? {
        var ourDate: String? = ourDate
        ourDate = try {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
            val value: Date = formatter.parse(ourDate)
            val dateFormatter =
                SimpleDateFormat("MM-dd-yyyy HH:mm") //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault())
            dateFormatter.format(value)

            //Log.d("ourDate", ourDate);
        } catch (e: Exception) {
            "00-00-0000 00:00"
        }
        return ourDate
    }

    fun String.getDateWithServerTimeStamp(): Date? {
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        )
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")  // IMP !!!
        try {
            return dateFormat.parse(this)
        } catch (e: ParseException) {
            return null
        }
    }

}
