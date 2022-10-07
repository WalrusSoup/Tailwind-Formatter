package com.github.walrussoup.tailwindformatternext

import java.util.*
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.github.walrussoup.tailwindformatternext.support.TailwindParser
import com.github.walrussoup.tailwindformatternext.support.TailwindSorter
import com.github.walrussoup.tailwindformatternext.support.TailwindUtility


@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class ParserTest : BasePlatformTestCase() {

    fun testCanSortPureCssFiles() {
        val utility = TailwindUtility();
        utility.loadDefaultClassOrder();

        val tailwindParser = TailwindParser(TailwindSorter(utility.classOrder, false))

        try {
            // read fixture file parser/input.css
            val input = myFixture.configureByFile("/parser/input.css").text
            val expected = myFixture.configureByFile("/parser/expected.css").text

            assertEquals(expected, tailwindParser.processBody(input));
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun testCanSortHtmlFiles() {
        val utility = TailwindUtility();
        utility.loadDefaultClassOrder();

        val tailwindParser = TailwindParser(TailwindSorter(utility.classOrder, false))

        try {
            // read fixture file parser/input.css
            val input = myFixture.configureByFile("/parser/input.html").text
            val expected = myFixture.configureByFile("/parser/expected.html").text

            assertEquals(expected, tailwindParser.processBody(input));
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun testCanSortVueFiles() {
        val utility = TailwindUtility();
        utility.loadDefaultClassOrder();

        val tailwindParser = TailwindParser(TailwindSorter(utility.classOrder, false))

        try {
            // read fixture file parser/input.css
            val input = myFixture.configureByFile("/parser/input.vue").text
            val expected = myFixture.configureByFile("/parser/expected.vue").text

            assertEquals(expected, tailwindParser.processBody(input));
        } catch (e: Exception) {
            println(e.message)
        }
    }
}
