package platformio.services


interface BoardService {
    fun loadAllBoards(): List<Board>
}

data class Board(
        val id: String,
        val name: String,
        val platform: String,
        val frameworks: Collection<String>,
        val mcu: String,
        val debug: String,
        val frequency: Frequency,
        val ram: Memory,
        val flash: Memory
)

inline class Frequency(val value: Int) {
    fun toMHz(): String {
        return "${(value / 1000000)} MHz"
    }
}

inline class Memory(val value: Int) {
    fun toKB(): String {
        return "${value / 1024} kB"
    }
}




