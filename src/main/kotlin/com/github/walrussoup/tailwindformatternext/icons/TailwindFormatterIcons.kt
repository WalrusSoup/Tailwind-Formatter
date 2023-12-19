package com.github.walrussoup.tailwindformatternext.icons

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

class TailwindFormatterIcons {
    companion object {
        @JvmField
        val DefaultIcon: Icon = IconLoader.getIcon("/icons/icon.svg", TailwindFormatterIcons::class.java)
        @JvmStatic
        fun getDefaultIcon(): Icon {
            return DefaultIcon
        }
    }
}