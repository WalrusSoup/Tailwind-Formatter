package com.github.walrussoup.tailwindformatternext.support

class CssHelper {

    companion object {
        var classNamesRegex: String = "[_a-zA-Z0-9\\s-:/~&\\[\\].>%]+";
        val assembledRegex =  "\\bclass(?:Name)*\\s*=\\s*(?<quotes>[\"'])(?<classList1>$classNamesRegex)[\"']|@apply (?<classList2>$classNamesRegex);"
        @JvmStatic
        fun getRegex(): String {
            return assembledRegex;
        }
        @JvmStatic
        fun getTailwindClassesRegex(): String {
            return classNamesRegex;
        }
    }
}