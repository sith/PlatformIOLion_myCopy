package platformio.services


interface BoardService {
    fun loadAllBoards(): Collection<Board>
}

class Board(val name: String)



