package platformio.services


interface FrameworkService {
    fun loadAllFrameworks():Collection<Framework>
}

class Framework(val name: String)
