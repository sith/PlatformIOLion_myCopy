package platformio.project

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessListener
import com.intellij.facet.ui.ValidationResult
import com.intellij.ide.util.projectWizard.AbstractNewProjectStep
import com.intellij.ide.util.projectWizard.CustomStepProjectGenerator
import com.intellij.ide.util.projectWizard.ProjectSettingsStepBase
import com.intellij.ide.util.projectWizard.SettingsStep
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.externalSystem.model.project.settings.ConfigurationData
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.openapi.wm.impl.welcomeScreen.AbstractActionWithPanel
import com.intellij.platform.DirectoryProjectGenerator
import com.intellij.platform.ProjectGeneratorPeer
import com.intellij.platform.WebProjectGenerator
import platformio.PlatformIO
import platformio.services.BoardService
import platformio.services.FrameworkService
import platformio.toolwindow.console.ID
import java.io.File
import javax.swing.Icon
import javax.swing.JComponent

class Generator : DirectoryProjectGenerator<Settings>, CustomStepProjectGenerator<ConfigurationData> {

    override fun generateProject(project: Project, baseDir: VirtualFile, settings: Settings, module: Module) {
        ApplicationManager.getApplication().runWriteAction {
            baseDir.createChildDirectory(this, ".idea")
            val init = GeneralCommandLine("platformio", "init", "--ide", "clion")
            init.workDirectory = File(baseDir.path)
            val processHandler = OSProcessHandler(init)

            processHandler.addProcessListener(object : ProcessListener {
                override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {}

                override fun processTerminated(event: ProcessEvent) {
                    VirtualFileManager.getInstance().asyncRefresh(null)
                }

                override fun processWillTerminate(event: ProcessEvent, willBeDestroyed: Boolean) {}

                override fun startNotified(event: ProcessEvent) {}
            })

            val console = TextConsoleBuilderFactory
                    .getInstance()
                    .createBuilder(project)
                    .console
            console.attachToProcess(processHandler)
            val contentManager = ToolWindowManager
                    .getInstance(project)
                    .getToolWindow(ID)
                    .contentManager
            val content = contentManager.factory.createContent(console.component, "platformio init", false)
            contentManager.addContent(content)
            processHandler.startNotify()
        }
    }

    override fun getName() = PlatformIO.name

    override fun getLogo(): Icon? = PlatformIO.icon

    override fun validate(baseDirPath: String): ValidationResult = ValidationResult.OK

    override fun createPeer(): Peer = Peer()

    override fun createStep(projectGenerator: DirectoryProjectGenerator<ConfigurationData>?, callback: AbstractNewProjectStep.AbstractCallback<ConfigurationData>?): AbstractActionWithPanel {
        return ProjectSettingsStepBase<ConfigurationData>(projectGenerator, callback)
    }
}

class Peer : ProjectGeneratorPeer<Settings> {
    val form = NewPIOProjectSettingsForm(ServiceManager.getService(BoardService::class.java), ServiceManager.getService(FrameworkService::class.java))

    override fun validate(): ValidationInfo? {
        return null
    }

    override fun getSettings(): Settings {
        return Settings(form.board, form.framework)
    }

    override fun addSettingsStateListener(listener: WebProjectGenerator.SettingsStateListener) {
    }

    override fun buildUI(settingsStep: SettingsStep) {
    }

    override fun isBackgroundJobRunning(): Boolean {
        return false
    }

    override fun getComponent(): JComponent {
        return form.component
    }
}
