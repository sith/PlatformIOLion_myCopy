package platformio.services.impl

import platformio.services.Board
import platformio.services.BoardService
import platformio.services.Frequency
import platformio.services.Memory

class CommandLineBoardService : BoardService {

    override fun loadAllBoards(): List<Board> {
        return listOf(Board(
                id = "idA",
                name = "someNameA",
                platform = "somePlatformA",
                frameworks = listOf("frameworkA", "frameworkB"),
                mcu = "someMCUA",
                debug = "someDebugA",
                frequency = Frequency(1000000),
                ram = Memory(1024),
                flash = Memory(2048)
        ), Board(
                id = "idA",
                name = "someNameA",
                platform = "somePlatformA",
                frameworks = listOf("frameworkA", "frameworkB"),
                mcu = "someMCUA",
                debug = "someDebugA",
                frequency = Frequency(1000000),
                ram = Memory(1024),
                flash = Memory(2048)))
    }
}