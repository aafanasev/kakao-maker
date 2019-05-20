package com.aafanasev.kakaomaker.idea

import com.intellij.ide.highlighter.XmlFileType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.fileChooser.FileChooserFactory
import com.intellij.openapi.fileChooser.FileSaverDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VirtualFile
import dev.aafanasev.kakaomaker.common.generator.KakaoScreenGenerator
import dev.aafanasev.kakaomaker.common.generator.ScreenParams
import java.io.File
import java.util.*

class GenerateKakaoScreenAction : AnAction() {

    override fun update(event: AnActionEvent) {
        val file = getSelectedFile(event)
        event.presentation.isEnabledAndVisible = file?.let { isSelectedFileAndroidLayout(it) } ?: false
    }

    override fun actionPerformed(event: AnActionEvent) {
        val file = getSelectedFile(event) ?: throw IllegalArgumentException("No file selected")
        val className = guessScreenNameFromFilename(file.nameWithoutExtension)

        val saverDescriptor = FileSaverDescriptor("Save Kakao screen", "", "kt")
        val saverDialog = FileChooserFactory.getInstance().createSaveFileDialog(saverDescriptor, event.project)

        val saved = saverDialog.save(file.parent, "$className.kt")

        // TODO: Do below code in background thread
        if (saved != null) {

            val generator = KakaoScreenGenerator()
            val files = TreeSet<String>()
            var body = "empty"
            try {
                body = generator.generate(
                        ScreenParams(className,"com.test","com.test"),
                        File(file.canonicalPath),
                        files
                )
            }catch (e:Exception) {
                body = e.message!!
            }

            FileUtil.writeToFile(saved.file, body, false)

            notifyFileCreated(event.project, saved.file)
        }
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
                .filterNot { IGNORED_LAYOUT_FILENAME_WORDS.contains(it) }
                .joinToString(separator = "", transform = { it.capitalize() })

        return screenName + "Screen"
    }

    private fun notifyFileCreated(project: Project?, file: File) {
        NotificationGroup
                .balloonGroup("KAKAO_FILE_CREATED_ID")
                .createNotification(
                        "Kakao Maker",
                        "${file.name} created",
                        NotificationType.INFORMATION,
                        null
                )
                .notify(project)
    }

    companion object {
        private val IGNORED_LAYOUT_FILENAME_WORDS = hashSetOf("activity", "fragment", "view", "include", "layout")
    }

}