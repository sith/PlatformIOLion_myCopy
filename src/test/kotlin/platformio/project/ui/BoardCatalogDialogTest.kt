package platformio.project.ui

import org.assertj.swing.data.TableCell
import org.assertj.swing.edt.GuiActionRunner
import org.assertj.swing.fixture.DialogFixture
import org.assertj.swing.fixture.JTableFixture
import org.hamcrest.Matchers.contains
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import platformio.services.Board
import javax.swing.JDialog

class BoardCatalogDialogTest {
    val dialog = BoardCatalogDialog(listOf(boardA, boardB))
    private val window = DialogFixture(GuiActionRunner.execute<JDialog> { dialog })

    @Before
    fun setUp() {
        window.show()
    }

    @After
    fun tearDown() {
        window.cleanUp()
    }

    @Test
    fun boardTableColumns() {
        window.textBox(BoardCatalogDialog.SEARCH_BOARD_TEXTFIELD_NAME)

        val table = window.table(BoardsTableModel.BOARD_TABLE_NAME)

        table.requireColumnCount(9)
        table.requireColumnNamed(BoardsTableModel.Column.CHECK_BOX_COLUMN.columnName)
        table.requireColumnNamed(BoardsTableModel.Column.NAME_COLUMN.columnName)
        table.requireColumnNamed(BoardsTableModel.Column.PLATFORM_COLUMN.columnName)
        table.requireColumnNamed(BoardsTableModel.Column.FRAMEWORK_COLUMN.columnName)
        table.requireColumnNamed(BoardsTableModel.Column.MCU_COLUMN.columnName)
        table.requireColumnNamed(BoardsTableModel.Column.DEBUG_COLUMN.columnName)
        table.requireColumnNamed(BoardsTableModel.Column.FREQUENCY_COLUMN.columnName)
        table.requireColumnNamed(BoardsTableModel.Column.RAM_COLUMN.columnName)
        table.requireColumnNamed(BoardsTableModel.Column.FLASH_COLUMN.columnName)
    }

    @Test
    fun boardTableRows() {

        val table = window.table(BoardsTableModel.BOARD_TABLE_NAME)
        table.requireRowCount(2)

        table.hasRow(0, boardA)
        table.hasRow(1, boardB)

    }

    @Test
    fun filtersTableContent() {
        val table = window.table(BoardsTableModel.BOARD_TABLE_NAME)

        window.textBox(BoardCatalogDialog.SEARCH_BOARD_TEXTFIELD_NAME).setText(boardB.name)
        table.requireRowCount(1)

        window.textBox(BoardCatalogDialog.SEARCH_BOARD_TEXTFIELD_NAME).setText(null)
        table.requireRowCount(2)
    }

    @Test
    fun returnsSelectedBoards() {
        val table = window.table(BoardsTableModel.BOARD_TABLE_NAME)
        table.cell(TableCell.row(1).column(0)).click()

        window.button(BoardCatalogDialog.OK_BUTTON_NAME).click()

        Assert.assertThat(dialog.selectedBoards, contains(boardB))
    }

    @Test
    fun returnsEmptySelectedBoardsIfCancelButtonPressed() {
        val table = window.table(BoardsTableModel.BOARD_TABLE_NAME)
        table.cell(TableCell.row(1).column(0)).click()

        window.button(BoardCatalogDialog.CANCEL_BUTTON_NAME).click()

        Assert.assertTrue(dialog.selectedBoards.isEmpty())
    }

    @Test
    fun returnsEmptySelectedBoardsIfDialogClosed() {
        val table = window.table(BoardsTableModel.BOARD_TABLE_NAME)
        table.cell(TableCell.row(1).column(0)).click()

        window.close()

        Assert.assertTrue(dialog.selectedBoards.isEmpty())
    }


    private fun JTableFixture.hasRow(rowIndex: Int, board: Board) {
        cell(TableCell.row(rowIndex).column(1)).requireValue(board.name)
        cell(TableCell.row(rowIndex).column(2)).requireValue(board.platform)
        cell(TableCell.row(rowIndex).column(3)).requireValue(board.framework)
        cell(TableCell.row(rowIndex).column(4)).requireValue(board.mcu)
        cell(TableCell.row(rowIndex).column(5)).requireValue(board.debug)
        cell(TableCell.row(rowIndex).column(6)).requireValue(board.frequency.toMHz())
        cell(TableCell.row(rowIndex).column(7)).requireValue(board.ram.toKB())
        cell(TableCell.row(rowIndex).column(8)).requireValue(board.flash.toKB())
    }
}