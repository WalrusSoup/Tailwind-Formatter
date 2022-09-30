package com.github.walrussoup.tailwindformatternext.actions

import com.github.walrussoup.tailwindformatternext.support.TailwindParser
import com.github.walrussoup.tailwindformatternext.support.TailwindSorter
import com.github.walrussoup.tailwindformatternext.support.TailwindUtility
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.FilenameIndex.getAllFilesByExt
import com.intellij.psi.search.GlobalSearchScope
import javax.swing.Icon

class TailwindFormatProjectAction : AnAction("Format Project")
{
    private val LOG = logger<TailwindFormatProjectAction>()
    private val extensions = listOf<String>("vue","blade","jsx","js","html")
    var isCustomConfiguration = false

    override fun actionPerformed(e: AnActionEvent)
    {
        LOG.info("Invoking project-wide formatting");
        val project = e.project;
        if(project == null) {
            LOG.info("No project found, returning");

            return;
        }
        val parser = TailwindParser(TailwindSorter(getClassOrder(project), isCustomConfiguration))
        LOG.info("Discovering vue files...");
        val files = getListOfProjectVirtualFilesByExt(project, false, "vue");
        LOG.info("${files.size} discovered")
        val allFiles = getListOfAllProjectVFiles(project);
        LOG.info("${allFiles.size} all files discovered")
        LOG.info(extensions.toString());

        allFiles.forEach {
            if(it.extension == null) {
                return@forEach;
            }
            if(!extensions.contains(it.extension)) {
                LOG.info("Extension list does not contain: ${it.extension}")
                return@forEach;
            }
            LOG.info("${it.canonicalPath} is valid for formatting");
            val document = FileDocumentManager.getInstance().getDocument(it);
            if(document == null) {
                LOG.info("${it.canonicalPath} failed opening, skipping?");
                return@forEach;
            }
            val body = parser.processBody(document.text);
            WriteCommandAction.runWriteCommandAction(project) { document.setText(body) }
        }
    }

    override fun update(e: AnActionEvent)
    {
        val project: Project? = e.project
        e.presentation.isEnabledAndVisible = project != null
    }

    // this don't work and I don't know why
    fun getListOfProjectVirtualFilesByExt(project: Project, caseSensitivity: Boolean = true, extName: String = "vue"): MutableCollection<VirtualFile> {
        val scope = GlobalSearchScope.projectScope(project)
        return FilenameIndex.getAllFilesByExt(project, extName, scope)
    }

    fun getListOfAllProjectVFiles(project: Project): MutableCollection<VirtualFile> {
        val collection = mutableListOf<VirtualFile>()
        ProjectFileIndex.getInstance(project).iterateContent {
            collection += it
            // Return true to process all the files (no early escape).
            true
        }
        return collection
    }

    // TODO: This should not be copy pasted
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
}