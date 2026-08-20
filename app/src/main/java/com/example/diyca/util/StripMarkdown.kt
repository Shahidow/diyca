package com.example.diyca.util

fun String.stripMarkdown(): String {
    return this.replace(Regex("""[*_~`#>]|\[.*?]|!\[.*?]\(.*?\)|^\s*[-+*]\s+"""), "")
        .trim()
}