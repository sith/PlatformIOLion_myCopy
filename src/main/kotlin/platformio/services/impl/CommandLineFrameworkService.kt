package platformio.services.impl

import platformio.services.Framework
import platformio.services.FrameworkService

class CommandLineFrameworkService : FrameworkService {
    override fun loadAllFrameworks(): Collection<Framework> {
        return emptyList()
    }
}