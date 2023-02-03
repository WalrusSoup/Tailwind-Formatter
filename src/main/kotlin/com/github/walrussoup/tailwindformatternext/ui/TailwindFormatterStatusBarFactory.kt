package com.github.walrussoup.tailwindformatternext.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory

class TailwindFormatterStatusBarFactory: StatusBarWidgetFactory
{
    companion object {
        private const val Id = "TailwindFormatter";
    }

    override fun getId(): String = Id;

    override fun getDisplayName(): String = "Tailwind Formatter"

    override fun isAvailable(project: Project): Boolean = true;

    override fun disposeWidget(widget: StatusBarWidget) {}

    override fun canBeEnabledOn(statusBar: StatusBar): Boolean = true;

    override fun createWidget(project: Project): StatusBarWidget {
        return TailwindFormatterStatusBarWidget();
    }
}