package com.github.walrussoup.tailwindformatternext

import com.github.walrussoup.tailwindformatternext.support.TailwindSorter
import com.github.walrussoup.tailwindformatternext.support.TailwindUtility
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.util.*


@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class SorterTest : BasePlatformTestCase() {

    fun testCanSortVariantsWithRespectToScreenSize() {
        val outOfOrderVariants = "mx-auto container py-4 xl:bg-blue-200 md:bg-blue-800 bg-blue-500 dark:bg-blue-600 lg:bg-blue-900 sm:bg-blue-700"
        // This expected order is per the official docs & tested via play.tailwindcss.com
        val expectedOrder = "container mx-auto bg-blue-500 py-4 dark:bg-blue-600 sm:bg-blue-700 md:bg-blue-800 lg:bg-blue-900 xl:bg-blue-200"
        val utility = TailwindUtility();
        utility.loadDefaultClassOrder()

        val sorter = TailwindSorter(utility.classOrder, false)
        val classes: List<String> = outOfOrderVariants.split(" ").toList();
        classes.sortedWith(sorter).joinToString(" ").let {
            assertEquals(expectedOrder, it)
        }
    }

    fun testSortingWithFlexVariants() {
        val input = "flex items-end justify-center p-4 text-center sm:items-center sm:p-0 min-h-full"
        val expected = "flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0"

        val utility = TailwindUtility()
        utility.loadDefaultClassOrder()

        val sorter = TailwindSorter(utility.classOrder, false)
        val classes: List<String> = input.split(" ").toList();

        classes.sortedWith(sorter).joinToString(" ").let {
            assertEquals(expected, it)
        }
    }

    fun testSortingVariantsAccordingToPrimaryClass() {
        val input = "sm:bg-red-500 sm:text-left sm:px-0 sm:rounded-lg"
        val expected = "sm:rounded-lg sm:bg-red-500 sm:px-0 sm:text-left"

        val utility = TailwindUtility()
        utility.loadDefaultClassOrder()

        val sorter = TailwindSorter(utility.classOrder, false)
        val classes: List<String> = input.split(" ").toList();

        classes.sortedWith(sorter).joinToString(" ").let {
            assertEquals(expected, it)
        }
    }
}
