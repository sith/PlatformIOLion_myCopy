package platformio.services.impl

import com.beust.klaxon.Klaxon
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.diagnostic.Logger
import org.jetbrains.concurrency.CancellablePromise
import platformio.services.Board
import platformio.services.Frequency
import platformio.services.Memory
import platformio.services.PlatformIOService
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors


class CommandLinePlatformIOService : PlatformIOService {
    val boards: CancellablePromise<List<Board>> = loadBoardsInternal()

    companion object {
        val log = Logger.getInstance(CommandLinePlatformIOService::class.java);
    }

    override fun loadAllBoards(): List<Board> {
        return boards.get()
    }

    private fun loadBoardsInternal(): CancellablePromise<List<Board>> {
        return ReadAction.nonBlocking(object : Callable<List<Board>> {
            override fun call(): List<Board> {
                try {
                    val commandLine = GeneralCommandLine("platformio", "boards", "--json-output")
                    return commandLine.createProcess().inputStream
                            .use { inputStream ->
                                Klaxon().parseArray<BoardModel>(inputStream)
                                        ?.map { it.toBoards() }
                                        ?.flatten()
                                        ?: emptyList()
                            }
                } catch (e: ExecutionException) {
                    log.error("Cannot load boards", e)
                    Notifications.Bus.notify(Notification("PlatformIO", "Cannot load boards", "Check logs for details.", NotificationType.ERROR))
                }
                return emptyList()
            }
        }).submit(Executors.newSingleThreadExecutor())
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



