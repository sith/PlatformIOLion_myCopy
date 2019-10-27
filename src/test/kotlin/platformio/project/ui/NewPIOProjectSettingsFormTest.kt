package platformio.project.ui

import org.assertj.swing.data.TableCell
import org.assertj.swing.edt.GuiActionRunner
import org.assertj.swing.fixture.DialogFixture
import org.assertj.swing.fixture.FrameFixture
import org.assertj.swing.fixture.JTableFixture
import org.assertj.swing.timing.Timeout
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import platformio.project.ui.BoardCatalogDialog.BOARD_CATALOG_DIALOG_NAME
import platformio.project.ui.NewPIOProjectSettingsForm.SELECT_BOARD_BUTTON_NAME
import platformio.services.Board
import platformio.services.PlatformIOService
import platformio.services.FrameworkService
import javax.swing.JFrame

@RunWith(MockitoJUnitRunner::class)
class NewPIOProjectSettingsFormTest {
    @Mock
    private lateinit var platformIOService: PlatformIOService
    @Mock
    private lateinit var form: NewPIOProjectSettingsForm

    private val frame = JFrame()
    private val window = FrameFixture(GuiActionRunner.execute<JFrame> { frame })

    private val boards = listOf(boardA, boardB, boardC)

    @Before
    fun setUp() {
        `when`(platformIOService.loadAllBoards()).thenReturn(boards)
    }

    private fun showWindow() {
        form = NewPIOProjectSettingsForm(platformIOService)
        frame.add(form.component)
        window.show()
    }

    @After
    fun tearDown() {
        window.cleanUp()
    }

    @Test
    fun picksBoards() {
        showWindow()

        selectBoards(boardA, boardC)

        val selectedBoardTable = window.table(NewPIOProjectSettingsForm.SELECTED_BOARD_TABLE_NAME)
        selectedBoardTable.requireRowCount(2)

        verifyColumnNames(selectedBoardTable)

        selectedBoardTable.boardExists(boardA, 0)
        selectedBoardTable.boardExists(boardC, 1)
    }


    @Test
    fun pickOneBoardAndThanAddAnotherOne() {
        showWindow()

        selectBoards(boardA)
        selectBoards(boardC) { it.table(BoardsTableModel.BOARD_TABLE_NAME).hasBoard(0, boardA, true) }

        val selectedBoardTable = window.table(NewPIOProjectSettingsForm.SELECTED_BOARD_TABLE_NAME)
        selectedBoardTable.requireRowCount(2)

        verifyColumnNames(selectedBoardTable)

        selectedBoardTable.boardExists(boardA, 0)
        selectedBoardTable.boardExists(boardC, 1)
    }


    private fun selectBoards(vararg boards: Board, extraAssertions: (DialogFixture) -> Any = {}) {
        window.button(SELECT_BOARD_BUTTON_NAME).click()
        val dialog = window.dialog(BOARD_CATALOG_DIALOG_NAME, Timeout.timeout(500))
        dialog.requireModal()
        extraAssertions.invoke(dialog)
        boards.forEach { dialog.selectBoards(it) }
        dialog.button(BoardCatalogDialog.OK_BUTTON_NAME).click()
    }

    private fun verifyColumnNames(selectedBoardTable: JTableFixture) {
        selectedBoardTable.requireColumnCount(3)
        selectedBoardTable.requireColumnNamed(NewPIOProjectSettingsForm.NAME_COLUMN)
        selectedBoardTable.requireColumnNamed(NewPIOProjectSettingsForm.PLATFORM_COLUMN)
        selectedBoardTable.requireColumnNamed(NewPIOProjectSettingsForm.FRAMEWORK_COLUMN)
    }

    private fun JTableFixture.boardExists(board: Board, row: Int) {
        cell(TableCell.row(row).column(0)).requireValue(board.name)
        cell(TableCell.row(row).column(1)).requireValue(board.platform)
        cell(TableCell.row(row).column(2)).requireValue(board.framework)
    }

    private fun DialogFixture.selectBoards(board: Board) {
        table(BoardsTableModel.BOARD_TABLE_NAME).cell(TableCell.row(boards.indexOf(board)).column(0)).click()
    }
}
