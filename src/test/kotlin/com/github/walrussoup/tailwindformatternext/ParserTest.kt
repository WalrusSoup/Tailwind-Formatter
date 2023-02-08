package com.github.walrussoup.tailwindformatternext

import java.util.*
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.github.walrussoup.tailwindformatternext.support.TailwindParser
import com.github.walrussoup.tailwindformatternext.support.TailwindSorter
import com.github.walrussoup.tailwindformatternext.support.TailwindUtility
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementWalkingVisitor
import com.intellij.psi.search.PsiElementProcessor
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import junit.framework.TestCase


@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class ParserTest : BasePlatformTestCase()
{
    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testCanSortPureCssFilesWithCustomOrder() {
        val utility = TailwindUtility();
        utility.classOrder = listOf("first", "sec_ond", "THIRD", "last", "group-focus:la-st", "sm:first", "lg:hover:last");

        val tailwindParser = TailwindParser(TailwindSorter(utility.classOrder, false))

        try {
            // read fixture file parser/custom-order-input.css
            val input = myFixture.configureByFile("/parser/custom-order-input.css").text
            val expected = myFixture.configureByFile("/parser/custom-order-expected.css").text

            assertEquals(expected, tailwindParser.processBody(input));
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun testCanSortCssFilesWithTailwindDefaultOrder()
    {
        val utility = TailwindUtility();
        utility.classOrder = listOf("first", "sec_ond", "THIRD", "last", "group-focus:la-st", "sm:first", "lg:hover:last");

        val tailwindParser = TailwindParser(TailwindSorter(utility.classOrder, false))

        try {
            // read fixture file parser/custom-order-input.css
            val input = myFixture.configureByFile("/parser/tailwind-order-input.css").text
            val expected = myFixture.configureByFile("/parser/tailwind-order-expected.css").text

            assertEquals(expected, tailwindParser.processBody(input));
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun testCanSortArbitraryVariants() {
        val utility = TailwindUtility();
        utility.loadDefaultClassOrder();
        val tailwindParser = TailwindParser(TailwindSorter(utility.classOrder, false))

        try {
            // read fixture file parser/custom-order-input.css
            val input = myFixture.configureByFile("/parser/arbitrary-variant-input.vue").text
            val expected = myFixture.configureByFile("/parser/arbitrary-variant-expected.vue").text

            assertEquals(expected, tailwindParser.processBody(input));
        } catch (e: Exception) {
            println(e.message)
            throw e;
        }
    }

    fun testCanSortHtmlFiles() {
        val utility = TailwindUtility();
        // make a new collection of strings with the order: first,sec_ond,THIRD,last,group-focus:la-st,sm:first,lg:hover:last
        utility.classOrder = listOf("first", "sec_ond", "THIRD", "last", "group-focus:la-st", "sm:first", "lg:hover:last");
        val tailwindParser = TailwindParser(TailwindSorter(utility.classOrder, true))

        try {
            // read fixture file parser/custom-order-input.css
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
            // read fixture file parser/custom-order-input.css
            val input = myFixture.configureByFile("/parser/input.vue").text
            val expected = myFixture.configureByFile("/parser/expected.vue").text

            assertEquals(expected, tailwindParser.processBody(input));
        } catch (e: Exception) {
            println(e.message)
        }
    }


    fun experimentalRubySupport() {
        val utility = TailwindUtility();

        utility.classOrder = listOf("hover:bg-red-600", "bg-red-500", "text-white", "shadow-sm", "border-transparent", "border-gray-300");

        val tailwindParser = TailwindParser(TailwindSorter(utility.classOrder, true))
        val input = myFixture.configureByFile("/parser/ruby-input.rb");
        val expected = myFixture.configureByFile("/parser/ruby-expected.rb");

        // This is complicated. The CSS matching results in nothing, and matching string literals is a bit difficult.
        // matching ruby strings is available outside of intellij community edition, but that would mean awkward support
        // for other versions that dont have ruby support
    }
}
