package com.github.walrussoup.tailwindformatternext.listeners

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.github.walrussoup.tailwindformatternext.services.MyProjectService

internal class MyProjectManagerListener : ProjectActivity {

    override suspend fun execute(project: Project) {
        project.service<MyProjectService>()
    }
}