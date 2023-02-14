package com.github.walrussoup.tailwindformatternext.actions

import com.github.walrussoup.tailwindformatternext.support.TailwindParser
import com.github.walrussoup.tailwindformatternext.support.TailwindSorter
import com.github.walrussoup.tailwindformatternext.support.TailwindUtility
import com.github.walrussoup.tailwindformatternext.ui.TailwindFormatterStatusBarWidget
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NotNull


class FormatFileAction : AnAction("Format Current File")
{
    private val LOG = logger<FormatProjectAction>()
    var isCustomConfiguration = false

    override fun actionPerformed(e: AnActionEvent) {
        LOG.info("Invoking format action");
        TailwindFormatterStatusBarWidget.updateText("Starting...", "Starting Formatter");
        // get current virtual file
        val currentFile : VirtualFile? = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if(currentFile === null) {
            LOG.info("No file open");
            return;
        }
        val editor: Editor = e.getRequiredData(CommonDataKeys.EDITOR);
        val project: Project = e.getRequiredData(CommonDataKeys.PROJECT);
        val document: Document = editor.document;
        LOG.info("Building Parser");

        ProgressManager.getInstance().run(object : Task.Backgroundable(null, "Format file") {
            override fun run(@NotNull progressIndicator: ProgressIndicator) {
                val parser = TailwindParser(TailwindSorter(getClassOrder(project), isCustomConfiguration))
                val body = parser.processBody(document.text);
                LOG.info("Writing sorted output");
                WriteCommandAction.runWriteCommandAction(project) { document.setText(body) }
                TailwindFormatterStatusBarWidget.updateText("Finished", "Finished Formatting ${currentFile.name}");
            }
        })
    }

    private fun getClassOrder(project: Project): List<String> {
        val utility = TailwindUtility()
        val configurationFile: VirtualFile? = VfsUtilCore.findRelativeFile(".tailwindorder", project.baseDir)
        if (configurationFile == null) {
            utility.loadDefaultClassOrder()
        } else {
            isCustomConfiguration = true;
            utility.loadClassOrderFromFile(configurationFile)
        }
        return utility.classOrder;
    }

    override fun update(e: AnActionEvent)
    {
        val project: Project? = e.project
        e.presentation.isEnabledAndVisible = project != null
    }
}