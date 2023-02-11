package com.github.walrussoup.tailwindformatternext

import com.github.walrussoup.tailwindformatternext.support.CssHelper
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
import java.util.regex.Pattern


@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class RegexTest : BasePlatformTestCase()
{
    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testClassRegexCanSeeTailwindClasses() {
        var tailwindClasses : String = "relative mt-2 overflow-hidden rounded bg-white text-sm shadow dark:bg-zinc-900 md:flex";
        var regex : String = CssHelper.getTailwindClassesRegex();

        val pattern = Pattern.compile(regex, Pattern.MULTILINE);
        val matcher = pattern.matcher(tailwindClasses);

        // it should match the entire block as valid tailwindcss classes
        assertEquals(1, matcher.results().count());
    }

    fun testClassRegexCanSeeTailwindClassesWithPseudoClasses() {
        var tailwindClasses : String = "relative mt-2 overflow-hidden rounded bg-white text-sm shadow dark:bg-zinc-900 md:flex group-focus:rounded";
        var regex : String = CssHelper.getTailwindClassesRegex();

        val pattern = Pattern.compile(regex, Pattern.MULTILINE);
        val matcher = pattern.matcher(tailwindClasses);

        // it should match the entire block as valid tailwindcss classes
        assertEquals(1, matcher.results().count());
    }

    fun testClassRegexCanSeeTailwindClassesWithMediaQueries() {
        var tailwindClasses : String = "relative mt-2 overflow-hidden rounded bg-white text-sm shadow dark:bg-zinc-900 md:flex sm:group-focus:rounded";
        var regex : String = CssHelper.getTailwindClassesRegex();

        val pattern = Pattern.compile(regex, Pattern.MULTILINE);
        val matcher = pattern.matcher(tailwindClasses);

        // it should match the entire block as valid tailwindcss classes
        assertEquals(1, matcher.results().count());
    }

    fun testClassRegexCanSeeTailwindClassesWithMediaQueriesAndPseudoClasses() {
        var tailwindClasses : String = "relative mt-2 overflow-hidden rounded bg-white text-sm shadow dark:bg-zinc-900 md:flex sm:group-focus:rounded md:hover:rounded";
        var regex : String = CssHelper.getTailwindClassesRegex();

        val pattern = Pattern.compile(regex, Pattern.MULTILINE);
        val matcher = pattern.matcher(tailwindClasses);

        // it should match the entire block as valid tailwindcss classes
        assertEquals(1, matcher.results().count());
    }

    fun testClassRegexCanSeeTailwindClassesWithMediaQueriesAndPseudoClassesAndVariants() {
        var tailwindClasses : String = "relative mt-2 overflow-hidden rounded bg-white text-sm shadow dark:bg-zinc-900 md:flex sm:group-focus:rounded md:hover:rounded dark:hover:rounded";
        var regex : String = CssHelper.getTailwindClassesRegex();

        val pattern = Pattern.compile(regex, Pattern.MULTILINE);
        val matcher = pattern.matcher(tailwindClasses);

        // it should match the entire block as valid tailwindcss classes
        assertEquals(1, matcher.results().count());
    }

    fun testClassRegexCanSeeTailwindClassesWithMediaQueriesAndPseudoClassesAndVariantsAndGrouping() {
        var tailwindClasses : String = "relative mt-2 overflow-hidden rounded bg-white text-sm shadow dark:bg-zinc-900 md:flex sm:group-focus:rounded md:hover:rounded dark:hover:rounded group-hover:rounded";
        var regex : String = CssHelper.getTailwindClassesRegex();

        val pattern = Pattern.compile(regex, Pattern.MULTILINE);
        val matcher = pattern.matcher(tailwindClasses);

        // it should match the entire block as valid tailwindcss classes
        assertEquals(1, matcher.results().count());
    }

    fun testClassRegexCanSeeTailwindClassesWithMediaQueriesAndPseudoClassesAndTrailingArbitraryVariants() {
        var tailwindClasses : String = "relative mt-2 overflow-hidden rounded bg-white text-sm shadow dark:bg-zinc-900 md:flex sm:group-focus:rounded md:hover:rounded dark:hover:rounded group-hover:rounded group-focus:rounded max-w-[75%]";
        var regex : String = CssHelper.getTailwindClassesRegex();

        val pattern = Pattern.compile(regex, Pattern.MULTILINE);
        val matcher = pattern.matcher(tailwindClasses);

        // it should match the entire block as valid tailwindcss classes
        assertEquals(1, matcher.results().count());
    }

    fun testClassRegexCanSeeTailwindClassesWithMediaQueriesAndPseudoClassesAndLeadingArbitraryVariants() {
        var tailwindClasses : String = "relative mt-2 overflow-hidden rounded bg-white text-sm shadow dark:bg-zinc-900 md:flex sm:group-focus:rounded md:hover:rounded dark:hover:rounded group-hover:rounded group-focus:rounded [.peer:checked~&>.material-icons]:opacity-100";
        var regex : String = CssHelper.getTailwindClassesRegex();

        val pattern = Pattern.compile(regex, Pattern.MULTILINE);
        val matcher = pattern.matcher(tailwindClasses);

        // it should match the entire block as valid tailwindcss classes
        assertEquals(1, matcher.results().count());
    }
}
