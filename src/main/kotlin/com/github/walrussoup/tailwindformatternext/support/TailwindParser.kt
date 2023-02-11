package com.github.walrussoup.tailwindformatternext.support

import java.util.*
import java.util.regex.Pattern

class TailwindParser(private val sorter: TailwindSorter) {

    fun processBody(body: String): String {
        var body = body
        val pattern = Pattern.compile(CssHelper.getRegex(), Pattern.MULTILINE)
        val matcher = pattern.matcher(body)
        while (matcher.find()) {
            val isApplyStatement: Boolean
            val originalClassList: String
            var quotes = matcher.group("quotes");
            if(quotes == null) {
                quotes = "";
            }

            // Grab the inner contents of the class list for deduplication and arranging
            if (matcher.group("classList1") != null && !matcher.group("classList1").isEmpty()) {
                isApplyStatement = false
                originalClassList = matcher.group("classList1")
            } else {
                isApplyStatement = true
                originalClassList = matcher.group("classList2")
            }
            if (originalClassList.contains("\n")) {
                // multiline class lists are currently not supported
                continue
            }
            val currentClasses =
                Arrays.asList(*originalClassList.trim { it <= ' ' }.split(" +".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray())
            // Sort the list of classes
            currentClasses.sortWith(sorter)
            // Create a linked hash set to remove duplicates
            val currentClassesWithoutDuplicates = LinkedHashSet(currentClasses)
            body = body.replace(
                encloseClassList(originalClassList, isApplyStatement, quotes),
                encloseClassList(java.lang.String.join(" ", currentClassesWithoutDuplicates), isApplyStatement, quotes)
            )
        }
        return body
    }

    private fun encloseClassList(classList: String, isApplyStatement: Boolean, quotes: String): String {
        if (isApplyStatement) {
            return "@apply $classList;"
        }
        return "$quotes$classList$quotes";
    }
}