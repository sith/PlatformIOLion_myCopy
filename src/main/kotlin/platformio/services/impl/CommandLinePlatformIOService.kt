package platformio.services.impl

import com.beust.klaxon.Klaxon
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.util.ExecUtil
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.Computable
import platformio.services.Board
import platformio.services.Frequency
import platformio.services.Memory
import platformio.services.PlatformIOService
import java.util.concurrent.ExecutionException


class CommandLinePlatformIOService : PlatformIOService {
    val boards: List<Board> = loadBoardsInternal()

    companion object {
        val log = Logger.getInstance(CommandLinePlatformIOService::class.java);
    }

    override fun loadAllBoards(): List<Board> {
        return boards
    }

    private fun loadBoardsInternal(): List<Board> {
        return ApplicationManager.getApplication().runReadAction(Computable<List<Board>> {
            try {
                val commandLine = GeneralCommandLine("platformio", "boards", "--json-output")
                val json = ExecUtil.execAndGetOutput(commandLine).stdout
                return@Computable Klaxon().parseArray<BoardModel>(json)?.map { it.toBoards() }?.flatten() ?: emptyList()
            } catch (e: ExecutionException) {
                log.error("Cannot load boards", e)
                Notifications.Bus.notify(Notification("PlatformIO", "Cannot load boards", "Check logs for details.", NotificationType.ERROR))
            }
            emptyList()
        });
    }

    data class BoardModel(
            var connectivity: Array<String>?,
            var debug: String? = null,
            val fcpu: Int,
            val frameworks: Array<String>,
            val id: String,
            val mcu: String,
            val name: String,
            val platform: String,
            val ram: Long,
            val rom: Long,
            val url: String,
            val vendor: String
    ) {
        fun toBoards(): Collection<Board> {
            return frameworks.map {
                Board(
                        id = id,
                        name = name,
                        platform = platform,
                        framework = it,
                        mcu = mcu,
                        frequency = Frequency(fcpu),
                        ram = Memory(ram),
                        flash = Memory(rom))
            }
        }
    }
}



