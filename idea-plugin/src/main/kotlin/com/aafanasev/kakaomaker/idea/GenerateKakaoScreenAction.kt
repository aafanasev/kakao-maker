package com.aafanasev.kakaomaker.idea

import com.intellij.ide.highlighter.XmlFileType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.vfs.VirtualFile

class GenerateKakaoScreenAction : AnAction() {

    override fun update(event: AnActionEvent) {
        val file = getSelectedFile(event)

        event.presentation.isEnabledAndVisible = file?.let { isSelectedFileAndroidLayout(it) } ?: false
    }

    override fun actionPerformed(event: AnActionEvent) {
        val file = getSelectedFile(event) ?: throw IllegalArgumentException("No file selected")

        // TODO: Generate screen class
        // TODO: Show file save dialog
    }

    private fun getSelectedFile(event: AnActionEvent): VirtualFile? {
        return event.getData(DataKeys.VIRTUAL_FILE)
    }

    /**
     * @return true, if the file is XML and inside layout directory
     */
    private fun isSelectedFileAndroidLayout(file: VirtualFile): Boolean {
        val isXmlFile = file.fileType == XmlFileType.INSTANCE
        val isInLayoutFolder = file.parent?.name?.startsWith("layout") ?: false

        return isXmlFile && isInLayoutFolder
    }

    /**
     * Guess Kakao screen name from a given layout filename
     *
     * For example, activity_main.xml -> MainScreen, fragment_list.xml -> ListScreen, etc
     */
    private fun guessScreenNameFromFilename(filename: String): String {
        val screenName = filename
                .split("_")
                .filter { IGNORED_LAYOUT_FILENAME_WORDS.contains(it) }
                .joinToString(separator = "", transform = { it.capitalize() })

        return screenName + "Screen"
    }

    companion object {
        private val IGNORED_LAYOUT_FILENAME_WORDS = hashSetOf("activity", "fragment", "view", "include", "layout")
    }

}