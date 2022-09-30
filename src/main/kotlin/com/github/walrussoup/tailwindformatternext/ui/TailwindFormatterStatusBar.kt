package com.github.walrussoup.tailwindformatternext.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.StatusBar

import com.intellij.openapi.wm.impl.status.EditorBasedWidget

class TailwindFormatterStatusBar(project: Project) : EditorBasedWidget(project) {
    override fun dispose() {
        TODO("Not yet implemented")
    }

    override fun ID(): String {
        return "TailwindFormatterStatusBar"
    }

    override fun install(statusBar: StatusBar) {
        TODO("Not yet implemented")
    }
}