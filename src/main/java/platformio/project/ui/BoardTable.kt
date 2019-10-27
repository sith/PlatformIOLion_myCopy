package platformio.project.ui

import platformio.services.Board
import javax.swing.JTable
import javax.swing.RowFilter
import javax.swing.table.TableRowSorter


class BoardTable(boards: List<Board>, selectedBoards: Set<Board>) {
    private val boardTableModel: BoardsTableModel = BoardsTableModel(boards, selectedBoards)

    val table = JTable(boardTableModel)
    private var rowSorter = TableRowSorter(boardTableModel)

    init {
        table.fillsViewportHeight = true
        table.name = BoardsTableModel.BOARD_TABLE_NAME
        table.rowSorter = rowSorter
    }

    fun clearSelectedBoards() = boardTableModel.clearSelectedBoards()

    fun getSelectedBoards(): Collection<Board> = boardTableModel.selectedBoards

    fun filterTable(text: String) {
        if (text.trim { it <= ' ' }.isEmpty()) {
            rowSorter.setRowFilter(null)
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter(text))
        }
    }
}