package platformio.project.ui

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.contains
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BoardsTableModelTest {

    val boardsTableModel = BoardsTableModel(listOf(boardA))


    @Test
    fun columnNames() {
        assertThat(boardsTableModel.columnCount, `is`(9))
        assertThat(boardsTableModel.getColumnName(0), `is`(BoardsTableModel.Column.CHECK_BOX_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(1), `is`(BoardsTableModel.Column.NAME_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(2), `is`(BoardsTableModel.Column.PLATFORM_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(3), `is`(BoardsTableModel.Column.FRAMEWORKS_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(4), `is`(BoardsTableModel.Column.MCU_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(5), `is`(BoardsTableModel.Column.DEBUG_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(6), `is`(BoardsTableModel.Column.FREQUENCY_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(7), `is`(BoardsTableModel.Column.RAM_COLUMN.columnName))
        assertThat(boardsTableModel.getColumnName(8), `is`(BoardsTableModel.Column.FLASH_COLUMN.columnName))
    }

    @Test
    fun rowCount() {
        assertThat(boardsTableModel.rowCount, `is`(1))
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
        assertThat(boardsTableModel.selectedBoards, contains(boardA))
    }

    @Test
    fun rowValues() {
        assertFalse(boardsTableModel.getValueAt(0, 0) as Boolean)
        assertThat(boardsTableModel.getValueAt(0, 1).toString(), `is`(boardA.name))
        assertThat(boardsTableModel.getValueAt(0, 2).toString(), `is`(boardA.platform))
        assertThat(boardsTableModel.getValueAt(0, 3).toString(), `is`(boardA.frameworks.joinToString(BoardsTableModel.LIST_DELIMITER)))
        assertThat(boardsTableModel.getValueAt(0, 4).toString(), `is`(boardA.mcu))
        assertThat(boardsTableModel.getValueAt(0, 5).toString(), `is`(boardA.debug))
        assertThat(boardsTableModel.getValueAt(0, 6).toString(), `is`(boardA.frequency.toMHz()))
        assertThat(boardsTableModel.getValueAt(0, 7).toString(), `is`(boardA.ram.toKB()))
        assertThat(boardsTableModel.getValueAt(0, 8).toString(), `is`(boardA.flash.toKB()))
    }
}