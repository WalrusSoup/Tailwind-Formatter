package com.github.walrussoup.tailwindformatternext.ui

import com.intellij.openapi.wm.CustomStatusBarWidget
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.impl.status.TextPanel
import com.intellij.util.concurrency.EdtExecutorService
import java.util.concurrent.TimeUnit

class TailwindFormatterStatusBarWidget() : TextPanel(), CustomStatusBarWidget
{
    var futureMethod = EdtExecutorService.getScheduledExecutorInstance().scheduleWithFixedDelay(this::updateState, 1, 3, TimeUnit.SECONDS);

    companion object {
        var currentText = "[TW] Idle";
        var currentTooltip = "Tailwind Formatter Not Running";

        fun updateText(text: String, toolTip: String) {
            currentText = "[TW] $text";
            currentTooltip = toolTip;
        }
    }

    override fun ID(): String {
        return "TailwindFormatter";
    }

    private fun updateState() {
        text = currentText;
        toolTipText = currentTooltip;
    };

    override fun install(statusBar: StatusBar) {
        updateState();
    }

    override fun getComponent(): TailwindFormatterStatusBarWidget = this

    override fun dispose() {}
}