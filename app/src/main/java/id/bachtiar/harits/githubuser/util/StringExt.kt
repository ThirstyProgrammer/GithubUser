package id.bachtiar.harits.githubuser.util

fun String?.defaultDash(): String = if (isNullOrEmpty()) "-" else this
fun String?.defaultEmpty(): String = if (isNullOrEmpty()) "" else this
fun String?.removeQueryParams(): String = this?.split("{")?.first().defaultEmpty()