package com.hhreck.myplugin1

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ide.CopyPasteManager
import java.awt.datatransfer.StringSelection
import java.net.URLEncoder
import java.awt.Desktop
import java.net.URI

class ErrorSearchAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {

        val editor = e.getData(PlatformDataKeys.EDITOR) ?: return
        val document = editor.document
        val selectedText = editor.selectionModel.selectedText

        val errorText = selectedText ?: document.text
        if (errorText.isBlank()) return

        val selection = StringSelection(errorText)
        CopyPasteManager.getInstance().setContents(selection)

        try {
            val encodedText = URLEncoder.encode(errorText, "UTF-8")
            val uri = URI("https://stackoverflow.com/search?q=$encodedText")
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
