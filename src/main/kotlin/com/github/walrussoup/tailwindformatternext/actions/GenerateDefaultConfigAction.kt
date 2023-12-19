package com.github.walrussoup.tailwindformatternext.actions

import com.github.walrussoup.tailwindformatternext.support.TailwindUtility
import com.github.walrussoup.tailwindformatternext.ui.TailwindFormatterStatusBarWidget
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir

class GenerateDefaultConfigAction: AnAction("Generate Default Config") {
    override fun actionPerformed(e: AnActionEvent) {
        if(e.project === null) {
            TailwindFormatterStatusBarWidget.updateText("Config Write Failed", "Failed Finding Project Directory")
            return
        }
        val project = e.project as Project
        val projectDir = project.guessProjectDir()
        if(projectDir == null) {
            TailwindFormatterStatusBarWidget.updateText("Config Write Failed", "Failed Finding Project Directory")
            return
        }
        val inputStream = TailwindUtility::class.java.getResourceAsStream("/classes.txt") ?: return

        // Setup this file in a write action operation so the IDE does not lock up
        ApplicationManager.getApplication().runWriteAction {
            if (projectDir.findChild(".tailwindorder")?.exists() != true) {
                val file = projectDir.findOrCreateChildData(this, ".tailwindorder")
                file.setBinaryContent(inputStream.readBytes())
                TailwindFormatterStatusBarWidget.updateText("Default Config Ready", "Finished Generating Default Configuration")
            } else {
                TailwindFormatterStatusBarWidget.updateText("Config Already Exists", "Delete configuration and try again.")
            }
        }
    }
}