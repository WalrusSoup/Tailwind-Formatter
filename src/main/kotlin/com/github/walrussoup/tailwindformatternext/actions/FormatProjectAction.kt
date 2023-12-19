package com.github.walrussoup.tailwindformatternext.actions

import com.github.walrussoup.tailwindformatternext.support.TailwindParser
import com.github.walrussoup.tailwindformatternext.support.TailwindSorter
import com.github.walrussoup.tailwindformatternext.support.TailwindUtility
import com.github.walrussoup.tailwindformatternext.ui.TailwindFormatterStatusBarWidget
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.runReadAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.annotations.NotNull

class FormatProjectAction : AnAction("Format Project")
{
    private val LOG = logger<FormatProjectAction>()
    private val extensions = listOf<String>("vue","blade","jsx","ts","tsx","js","html")
    var isCustomConfiguration = false

    override fun actionPerformed(e: AnActionEvent)
    {
        // Not using com.intellij.openapi.progress.runBackgroundableTask as we lose scope of the project
        ProgressManager.getInstance().run(object : Task.Backgroundable(null, "Format file") {
            override fun run(@NotNull progressIndicator: ProgressIndicator) {
                val project = e.project
                if (project == null) {
                    LOG.info("No project found, returning")
                    return
                }
                TailwindFormatterStatusBarWidget.updateText("Starting", "Starting Project Wide Format")
                progressIndicator.isIndeterminate = false
                LOG.info("Invoking project-wide formatting")
                val parser = TailwindParser(TailwindSorter(getClassOrder(project), isCustomConfiguration))

                // List all files in the project
                val allFiles = kotlin.runCatching {
                    runReadAction { getListOfAllProjectVFiles(project) }
                }.getOrDefault(listOf<VirtualFile>())

                LOG.info("Found ${allFiles.size} files in project")
                var fileCount = 0
                val totalFiles = allFiles.size

                allFiles.forEach {
                    if (progressIndicator.isCanceled) {
                        LOG.info("User cancelled formatting")
                        return@forEach
                    }
                    if (it.extension == null || !extensions.contains(it.extension)) {
                        return@forEach
                    }
                    progressIndicator.text = "Checking ${it.name}"
                    progressIndicator.fraction = fileCount.toDouble() / totalFiles.toDouble()
                    LOG.info("${it.canonicalPath} is valid for formatting")

                    val document = kotlin.runCatching {
                        runReadAction { FileDocumentManager.getInstance().getDocument(it) }
                    }.getOrDefault(null)

                    if (document == null) {
                        LOG.info("No document found for ${it.name}, skipping")
                        return@forEach
                    }
                    LOG.info("Processing ${it.name}...")
                    val body = parser.processBody(document.text)
                    WriteCommandAction.runWriteCommandAction(project) { document.setText(body) }
                    TailwindFormatterStatusBarWidget.updateText("Running", "Formatting ${it.name}")
                    fileCount++
                }
                TailwindFormatterStatusBarWidget.updateText(
                    "Finished Project Format",
                    "Finished formatting $fileCount files."
                )
            }
        })
    }

    override fun update(e: AnActionEvent)
    {
        val project: Project? = e.project
        e.presentation.isEnabledAndVisible = project != null
    }

    // this doesn't work and I don't know why
    fun getListOfProjectVirtualFilesByExt(project: Project,  extName: String = "vue"): MutableCollection<VirtualFile> {
        val scope = GlobalSearchScope.projectScope(project)
        return FilenameIndex.getAllFilesByExt(project, extName, scope)
    }

    /**
     * Builds a collection, containing of all files in the project.
     */
    fun getListOfAllProjectVFiles(project: Project): List<VirtualFile> {
        val collection = mutableListOf<VirtualFile>()
        ProjectFileIndex.getInstance(project).iterateContent {
            collection += it
            // Return true to process all the files (no early escape).
            true
        }
        return collection.toList()
    }

    // TODO: This should not be copy pasted
    private fun getClassOrder(project: Project): List<String> {
        val utility = TailwindUtility()
        val configurationFile: VirtualFile? = VfsUtilCore.findRelativeFile(".tailwindorder", project.baseDir)
        if (configurationFile == null) {
            utility.loadDefaultClassOrder()
        } else {
            isCustomConfiguration = true
            utility.loadClassOrderFromFile(configurationFile)
        }
        return utility.classOrder
    }
}