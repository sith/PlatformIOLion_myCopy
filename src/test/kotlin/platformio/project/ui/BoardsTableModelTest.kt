package platformio.project.ui

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.contains
import org.junit.Test
import platformio.services.Board
import kotlin.test.assertTrue

class BoardsTableModelTest {

    val boardsTableModel = BoardsTableModel(listOf(boardA, boardB), setOf(boardB))


    @Test
    fun columnNames() {
        assertThat(boardsTableModel.columnCount, `is`(8))
        assertThat(boardsTableModel.getColumnName(0), `is`(BoardsTableModel.Column.CHECK_BOX_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(1), `is`(BoardsTableModel.Column.NAME_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(2), `is`(BoardsTableModel.Column.PLATFORM_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(3), `is`(BoardsTableModel.Column.FRAMEWORK_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(4), `is`(BoardsTableModel.Column.MCU_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(5), `is`(BoardsTableModel.Column.FREQUENCY_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(6), `is`(BoardsTableModel.Column.RAM_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(7), `is`(BoardsTableModel.Column.FLASH_COLUMN.columnName))
    }

    @Test
    fun rowCount() {
        assertThat(boardsTableModel.rowCount, `is`(2))
    }

    @Test
    fun clearSelectedBoards() {
        boardsTableModel.setValueAt(true, 0, 0)
        boardsTableModel.clearSelectedBoards()
        assertTrue(boardsTableModel.selectedBoards.isEmpty())
    }

    @Test
    fun setValue() {
        boardsTableModel.setValueAt(true, 0, 0)
        assertThat(boardsTableModel.selectedBoards, contains(boardA, boardB))
    }

    @Test
    fun rowValues() {
        boardA.existsInRow(0, false)
        boardB.existsInRow(1, true)
    }

    private fun Board.existsInRow(row: Int, selected: Boolean) {
        assertThat(boardsTableModel.getValueAt(row, 0) as Boolean, `is`(selected))
        assertThat(boardsTableModel.getValueAt(row, 1).toString(), `is`(name))
        assertThat(boardsTableModel.getValueAt(row, 2).toString(), `is`(platform))
        assertThat(boardsTableModel.getValueAt(row, 3).toString(), `is`(framework))
        assertThat(boardsTableModel.getValueAt(row, 4).toString(), `is`(mcu))
        assertThat(boardsTableModel.getValueAt(row, 5).toString(), `is`(frequency.toMHz()))
        assertThat(boardsTableModel.getValueAt(row, 6).toString(), `is`(ram.toKB()))
        assertThat(boardsTableModel.getValueAt(row, 7).toString(), `is`(flash.toKB()))
    }
}