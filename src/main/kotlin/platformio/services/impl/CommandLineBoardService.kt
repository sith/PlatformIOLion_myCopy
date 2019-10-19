package platformio.services.impl

import platformio.services.Board
import platformio.services.BoardService

class CommandLineBoardService : BoardService {
    override fun loadAllBoards(): Collection<Board> {
        return emptyList()
    }
}