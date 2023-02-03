package com.github.walrussoup.tailwindformatternext.support

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.vfs.VirtualFile
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors

class TailwindUtility {
    var classOrder = emptyList<String>()
    fun loadDefaultClassOrder() {
        logger.info("Loading default class order")
        try {
            val inputStream = TailwindUtility::class.java.getResourceAsStream("/classes.txt")
            if(inputStream == null) {
                logger.error("Error resolving class order from resources")
                return
            }
            classOrder = BufferedReader(InputStreamReader(inputStream)).lines().collect(Collectors.toList())
        } catch (e: Exception) {
            // I should probably do something here on the off chance we're missing that file
            logger.error("Error resolving class order from resources")
            logger.error(e.message)
        }
    }

    fun loadClassOrderFromFile(file: VirtualFile) {
        logger.info("Loading custom class order from user default .tailwindorder")
        try {
            val inputStream = file.inputStream
            classOrder = BufferedReader(InputStreamReader(inputStream)).lines().collect(Collectors.toList())
        } catch (e: Exception) {
            // I should probably do something here on the off chance we're missing that file
            logger.error("Error resolving class order from user defined .tailwindorder file, will fall back to defaults")
            logger.error(e.message)
            loadDefaultClassOrder()
        }
    }

    companion object {
        private val logger = Logger.getInstance(
            TailwindUtility::class.java
        )
    }
}