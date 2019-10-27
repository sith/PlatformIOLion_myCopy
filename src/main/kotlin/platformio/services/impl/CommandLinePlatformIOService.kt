package platformio.services.impl

import platformio.services.Board
import platformio.services.PlatformIOService
import platformio.services.Frequency
import platformio.services.Memory

class CommandLinePlatformIOService : PlatformIOService {

    override fun loadAllBoards(): List<Board> {
        return listOf(Board(
                id = "idA",
                name = "someNameA",
                platform = "somePlatformA",
                framework = "frameworkA",
                mcu = "someMCUA",
                debug = "someDebugA",
                frequency = Frequency(1000000),
                ram = Memory(1024),
                flash = Memory(2048)
        ), Board(
                id = "idB",
                name = "someNameB",
                platform = "somePlatformB",
                framework = "frameworkB",
                mcu = "someMCUB",
                debug = "someDebugB",
                frequency = Frequency(1000000),
                ram = Memory(1024),
                flash = Memory(2048)))
    }
}