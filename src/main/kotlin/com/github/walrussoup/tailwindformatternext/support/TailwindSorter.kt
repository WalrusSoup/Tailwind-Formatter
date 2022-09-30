package com.github.walrussoup.tailwindformatternext.support

import java.util.*
import java.util.stream.Collectors

class TailwindSorter(classOrder: List<String>, isCustomConfiguration: Boolean) : Comparator<String> {
    private val classOrder: List<String>
    private val variantsStartAt: Int
    private var lastPosition = 0
    private val isCustomConfiguration: Boolean
    private val variantOrder = Arrays.asList(
        "first",
        "last",
        "odd",
        "even",
        "visited",
        "checked",
        "empty",
        "read-only",
        "group-hover",
        "group-focus",
        "focus-within",
        "hover",
        "focus",
        "focus-visible",
        "active",
        "disabled",
        "dark",
        "xs",
        "sm",
        "md",
        "lg",
        "xl",
        "2xl",
        "3xl",
        "4xl",
        "5xl"
    )

    init {
        // convert all of these to lowercase for comparison later
        this.classOrder = classOrder.stream().map { s: String -> s.lowercase(Locale.getDefault()) }
            .collect(Collectors.toList())
        variantsStartAt = classOrder.size
        if (!isCustomConfiguration) {
            lastPosition = variantsStartAt + variantOrder.size
        } else {
            lastPosition = variantsStartAt
        }
        this.isCustomConfiguration = isCustomConfiguration
    }

    override fun compare(s: String, t1: String): Int {
        var positionS = calculateProperOrder(s.lowercase(Locale.getDefault()))
        var positionT1 = calculateProperOrder(t1.lowercase(Locale.getDefault()))
        if (positionS == -1) {
            positionS = lastPosition
        }
        if (positionT1 == -1) {
            positionT1 = lastPosition
        }
        return positionS - positionT1
    }

    // If the class contains a variant, use the variant order
    // Otherwise, use the default class order as they appear in the CSS (pre-generated)
    fun calculateProperOrder(className: String): Int {
        return if (className.contains(":") && !isCustomConfiguration) {
            orderByVariant(className)
        } else classOrder.indexOf(className)
    }

    /**
     * Variants are strange because they have their own order. Instead of generating an even more massive tailwind file,
     * we can try to detect it and leverage the order of variants given to us in the tailwind config file to sort them.
     *
     * Variants also have order within them, so when we fish out a variant also add the original class offset to it
     * This ensures that sm:px-2 is after things such as sm:bg-red-500, which is the way tailwind prefers it.
     * It's a little confusing to add the class order size, but it's the only way to make sure the variants AND the classes don't run over each other.
     * Open to ideas there
     *
     *
     * @param className string class to be checked
     */
    fun orderByVariant(className: String): Int {
        val variantLocation = className.lastIndexOf(":")
        // If we see no variants, just return 0
        if (variantLocation == -1) {
            return 0
        }
        // fish out the first variant to use as an anchor point
        val variants = className.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (variants.isNotEmpty()) {
            return (variantsStartAt + variantOrder.indexOf(variants[0]) * classOrder.size + calculateProperOrder(variants[1]))
        }
        return variantsStartAt;
    }
}