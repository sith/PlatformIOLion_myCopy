package platformio.project.ui

import platformio.services.Board
import platformio.services.Frequency
import platformio.services.Memory


val boardA = Board(
        id = "idA",
        name = "someNameA",
        platform = "somePlatformA",
        frameworks = listOf("frameworkA", "frameworkB"),
        mcu = "someMCUA",
        debug = "someDebugA",
        frequency = Frequency(1000000),
        ram = Memory(1024),
        flash = Memory(2048)
)
val boardB = Board(
        id = "idB",
        name = "someNameB",
        platform = "somePlatformB",
        frameworks = listOf("frameworkAA", "frameworkBB"),
        mcu = "someMCUB",
        debug = "someDebugB",
        frequency = Frequency(2000000),
        ram = Memory(3072),
        flash = Memory(4096)
)