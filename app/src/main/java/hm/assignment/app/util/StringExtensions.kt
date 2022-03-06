package hm.assignment.app.util

import android.util.Log

/**
 * Created on 2022-03-05.
 * CopyrightⒸ Kagge
 */


fun String.log() {
    Log.d("HMLog", this)
}

fun Throwable.logError() {
    Log.w("HMLog", this)
}