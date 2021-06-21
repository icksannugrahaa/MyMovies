package com.sh.s1.made.mymovies.core.utils

object MyUtils {
    fun toHourStringFormat(clock: Int?): String {
        val hour: Int = clock!! / 60
        val minute: Int = clock % 60

        return StringBuilder("$hour hour $minute minute").toString()
    }
}